package io.github.marrafon91.dscatalog.mappers;

import io.github.marrafon91.dscatalog.dto.UserDTO;
import io.github.marrafon91.dscatalog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDTO dto);

    UserDTO toDTO(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateEntityFromDTO(UserDTO dto, @MappingTarget User entity);
}
