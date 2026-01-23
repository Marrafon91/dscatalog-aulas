package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.DepartmentDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository repository;

    public List<DepartmentDTO> findAll() {
        List<Department> list = repository.findAll(by("name"));
        return list.stream().map(DepartmentDTO::new).toList();
    }
}
