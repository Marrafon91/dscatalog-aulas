package io.github.marrafon91.dscatalog.tests;

import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L,"Phone", "Good Phone", 800.0, "https://img.com.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
