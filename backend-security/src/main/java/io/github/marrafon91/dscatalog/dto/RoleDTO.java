package io.github.marrafon91.dscatalog.dto;

import io.github.marrafon91.dscatalog.entities.Role;

public record RoleDTO(
        Long id,
        String authority
) {

    public RoleDTO(Role role) {
        this(
                role.getId(),
                role.getAuthority()
        );
    }
}
