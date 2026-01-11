package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Product;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl,
        Instant date,
        List<CategoryDTO> categories
) {
    // Construtor canônico compacto
    public ProductDTO {
        categories = categories == null ? List.of() : List.copyOf(categories);
    }

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
                        .collect(Collectors.toList())
        );
    }
}
