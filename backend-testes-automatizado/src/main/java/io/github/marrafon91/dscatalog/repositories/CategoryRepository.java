package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
