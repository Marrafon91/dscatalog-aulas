package io.github.marrafon91.dscatalog.repositories;

import io.github.marrafon91.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
