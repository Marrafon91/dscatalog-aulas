package io.github.marrafon91.dscatalog.services.copy;

import io.github.marrafon91.dscatalog.dto.CategoryDTO;
import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.entities.Product;

public interface copyDtoToEntity {

    default void copy(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category cat = new Category();
            cat.setId(catDTO.getId());
            entity.getCategories().add(cat);
        }
    }
}
