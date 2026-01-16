package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.Product;
import io.github.marrafon91.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId = 1L;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        countTotalProducts = 25L;
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        assertFalse(result.isPresent());
    }

    @Test
    void saveShouldPersistWithAutoIncrementWhenIdisNull() {

        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        assertNotNull(product.getId());
        assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    void findByIdShouldReturnOptionalProductWhenIdExists() {
        Product product = Factory.createProduct();
        product = repository.save(product);

        Optional<Product> result = repository.findById(product.getId());

        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
    }
}
