package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.mappers.ProductMapper;
import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Product;
import io.github.marrafon91.dscatalog.repositories.ProductRepository;
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
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found. Id: " + id));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(ProductDTO::new);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            mapper.updateEntityFromDTO(dto, entity);
            entity = repository.save(entity);
            return mapper.toDTO(entity);
        }
        catch (EntityNotFoundException e) {
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
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity violation");
        }
    }
}
