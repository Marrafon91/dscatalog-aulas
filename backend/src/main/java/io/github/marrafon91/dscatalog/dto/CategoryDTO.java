package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDTO(

        Long id,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 100, message = "Campo fora do tamanho padrão")
        String name
) {
    public CategoryDTO(Category entity) {
        this(
                entity.getId(),
                entity.getName()
        );
    }
}
