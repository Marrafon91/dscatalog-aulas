package io.github.marrafon91.dscatalog.mappers;

import io.github.marrafon91.dscatalog.dto.CategoryDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDTO dto);

    CategoryDTO toDTO(Category entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(CategoryDTO dto, @MappingTarget Category entity);
}
