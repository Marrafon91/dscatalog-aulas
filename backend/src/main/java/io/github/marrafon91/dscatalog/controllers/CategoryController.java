package io.github.marrafon91.dscatalog.controllers;

import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }
}
