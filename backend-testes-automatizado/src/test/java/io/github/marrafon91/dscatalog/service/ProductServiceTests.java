package io.github.marrafon91.dscatalog.service;

import io.github.marrafon91.dscatalog.repositories.ProductRepository;
import io.github.marrafon91.dscatalog.services.ProductService;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;

        doNothing().when(repository).deleteById(existingId);
        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
    }

    @Test
    void deleteShouldDoNothingWhenIdExist() {
        assertDoesNotThrow(() -> service.delete(existingId)
        );
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(nonExistingId)
        );
    }
}
