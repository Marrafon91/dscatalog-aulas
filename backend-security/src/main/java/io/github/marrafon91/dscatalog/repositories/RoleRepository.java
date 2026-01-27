package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
