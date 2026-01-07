package io.github.marrafon91.dscatalog.controllers;

import io.github.marrafon91.dscatalog.controllers.mappers.CategoryMapper;
import io.github.marrafon91.dscatalog.dto.CategoryDTO;
import io.github.marrafon91.dscatalog.entities.Category;
import io.github.marrafon91.dscatalog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController implements GenericController {

    @Autowired
    private CategoryService service;


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {
        CategoryDTO result = service.insert(dto);
        URI uri = headerLocation(dto.id());
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody CategoryDTO dto) {
        CategoryDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }
}
