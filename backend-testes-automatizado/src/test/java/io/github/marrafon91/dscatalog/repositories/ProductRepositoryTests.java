package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId = 1L;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
  
        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        assertFalse(result.isPresent());
    }
}
