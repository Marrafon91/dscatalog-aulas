package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 100, message = "Campo deve ter de 3 a 100 caracteres")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
