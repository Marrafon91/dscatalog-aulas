package io.github.marrafon91.dscatalog.mappers;

import io.github.marrafon91.dscatalog.dto.RoleDTO;
import io.github.marrafon91.dscatalog.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "authority", source = "authority")
    Role toEntity(RoleDTO dto);

    RoleDTO toDTO(Role entity);
}
