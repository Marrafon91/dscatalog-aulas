package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.mappers.CategoryMapper;
import io.github.marrafon91.dscatalog.dto.CategoryDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found. Id: " + id));
        return new CategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<Category> result = repository.findAll(pageable);
        return result.map(CategoryDTO::new);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            mapper.updateEntityFromDTO(dto, entity);
            entity = repository.save(entity);
            return mapper.toDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found. Id: " + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found. Id: " + id);
        }
        try {
           repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity violation");
        }

    }

}
