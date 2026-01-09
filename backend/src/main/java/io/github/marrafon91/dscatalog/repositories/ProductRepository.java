package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
