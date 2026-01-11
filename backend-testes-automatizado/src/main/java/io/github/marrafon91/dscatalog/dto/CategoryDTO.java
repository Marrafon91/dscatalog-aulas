package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDTO(

        Long id,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 100, message = "Campo deve ter de 3 a 100 caracteres")
        String name
) {
    public CategoryDTO(Category entity) {
        this(
                entity.getId(),
                entity.getName()
        );
    }
}
