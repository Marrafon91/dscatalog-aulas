package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.entities.Product;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl,
        Instant date,
        List<CategoryDTO> categories
) {

    // Construtor canônico: garante imutabilidade defensiva
    public ProductDTO {
        categories = categories == null
                ? List.of()
                : List.copyOf(categories);
    }

    // Construtor a partir da entidade Product
    public ProductDTO(Product entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getDate(),
                entity.getCategories()
                        .stream()
                        .map(CategoryDTO::new)
                        .toList()
        );
    }

    // Construtor alternativo usando Set<Category>
    public ProductDTO(Product entity, Set<Category> categories) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getDate(),
                categories
                        .stream()
                        .map(CategoryDTO::new)
                        .toList()
        );
    }
}
