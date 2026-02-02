package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.dto.CategoryDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.mappers.ProductMapper;
import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Product;
import io.github.marrafon91.dscatalog.repositories.CategoryRepository;
import io.github.marrafon91.dscatalog.repositories.ProductRepository;
import io.github.marrafon91.dscatalog.services.copy.copyDtoToEntity;
import io.github.marrafon91.dscatalog.services.exceptions.DatabaseException;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService implements copyDtoToEntity {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found. Id: " + id));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> page = repository.findAllWithCategories(pageable);
        return page.map(p -> new ProductDTO(p, p.getCategories()));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copy(dto, entity);
        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }

        entity = repository.save(entity);
        return new ProductDTO(entity);
    }


    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copy(dto, entity);

            entity.getCategories().clear();
            for (CategoryDTO catDTO : dto.getCategories()) {
                Category category = categoryRepository.getReferenceById(catDTO.getId());
                entity.getCategories().add(category);
            }

            entity = repository.save(entity);
            return new ProductDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Product not found. Id: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found. Id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity violation");
        }
    }
}
