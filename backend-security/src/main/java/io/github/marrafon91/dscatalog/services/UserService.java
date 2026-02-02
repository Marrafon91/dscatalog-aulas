package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.dto.RoleDTO;
import io.github.marrafon91.dscatalog.dto.UserDTO;
import io.github.marrafon91.dscatalog.dto.UserInsertDTO;
import io.github.marrafon91.dscatalog.dto.UserUpdateDTO;
import io.github.marrafon91.dscatalog.entities.Role;
import io.github.marrafon91.dscatalog.entities.User;
import io.github.marrafon91.dscatalog.mappers.UserMapper;
import io.github.marrafon91.dscatalog.projections.UserDetailsProjection;
import io.github.marrafon91.dscatalog.repositories.RoleRepository;
import io.github.marrafon91.dscatalog.repositories.UserRepository;
import io.github.marrafon91.dscatalog.services.exceptions.DatabaseException;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setEmail(result.getFirst().getUserName());
        user.setPassword(result.getFirst().getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }


    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found. Id: " + id));
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(UserDTO::new);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = mapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }

        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getReferenceById(id);

            mapper.updateEntityFromDTO(dto, entity);

            entity.getRoles().clear();
            for (RoleDTO roleDTO : dto.getRoles()) {
                Role role = roleRepository.getReferenceById(roleDTO.getId());
                entity.getRoles().add(role);
            }

            entity = repository.save(entity);
            return mapper.toDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User not found. Id: " + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found. Id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity violation");
        }
    }

}