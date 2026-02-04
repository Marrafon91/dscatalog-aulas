package io.github.marrafon91.dscatalog.service;

import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.entities.Product;
import io.github.marrafon91.dscatalog.repositories.CategoryRepository;
import io.github.marrafon91.dscatalog.repositories.ProductRepository;
import io.github.marrafon91.dscatalog.services.ProductService;
import io.github.marrafon91.dscatalog.services.exceptions.DatabaseException;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import io.github.marrafon91.dscatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        Product product = Factory.createProduct();
        PageImpl<Product> page = new PageImpl<>(List.of(product));

        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        when(repository.save(any(Product.class))).thenReturn(product);

        doNothing().when(repository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        ProductDTO dto = new ProductDTO(Factory.createProduct());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(nonExistingId, dto));

        verify(repository, times(1)).getReferenceById(nonExistingId);
        verify(repository, never()).save(any());
    }

    @Test
    void updateShouldReturnProductDTOWhenIdExists() {

        Product product = Factory.createProduct();
        product.setId(existingId);

        ProductDTO dto = new ProductDTO(product);

        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.save(any(Product.class))).thenReturn(product);
        when(categoryRepository.getReferenceById(anyLong()))
                .thenReturn(new Category());

        ProductDTO result = service.update(existingId, dto);

        assertNotNull(result);
        assertEquals(existingId, result.getId());

        verify(repository, times(1)).getReferenceById(existingId);
        verify(repository, times(1)).save(any(Product.class));
        verify(categoryRepository, atLeastOnce()).getReferenceById(anyLong());
    }


    @Test
    void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO productDTO = service.findById(existingId);

        assertNotNull(productDTO);
        assertEquals(existingId, productDTO.getId());

        verify(repository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class,
                () -> service.findById(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> page = new PageImpl<>(
                List.of(Factory.createProduct())
        );

        when(repository.findAllWithCategories(pageable))
                .thenReturn(page);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(repository, times(1))
                .findAllWithCategories(pageable);
    }


    @Test
    void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

    @Test
    void deleteShouldDoNothingWhenIdExist() {
        assertDoesNotThrow(() -> service.delete(existingId)
        );
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(nonExistingId)
        );
    }
}
