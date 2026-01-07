package io.github.marrafon91.dscatalog.services;

import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }
}
