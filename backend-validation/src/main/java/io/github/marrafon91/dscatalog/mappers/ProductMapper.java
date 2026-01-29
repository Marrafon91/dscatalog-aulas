package io.github.marrafon91.dscatalog.mappers;

import io.github.marrafon91.dscatalog.dto.ProductDTO;
import io.github.marrafon91.dscatalog.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product toEntity(ProductDTO dto);

    ProductDTO toDTO(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromDTO(ProductDTO dto, @MappingTarget Product entity);
}
