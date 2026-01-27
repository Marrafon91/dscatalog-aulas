package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.dto.UserDTO;
import io.github.marrafon91.dscatalog.entities.User;
import io.github.marrafon91.dscatalog.mappers.UserMapper;
import io.github.marrafon91.dscatalog.repositories.UserRepository;
import io.github.marrafon91.dscatalog.services.exceptions.DatabaseException;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

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
    public UserDTO insert(UserDTO dto) {
        User entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            mapper.updateEntityFromDTO(dto, entity);
            entity = repository.save(entity);
            return mapper.toDTO(entity);
        }
        catch (EntityNotFoundException e) {
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
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity violation");
        }
    }
}