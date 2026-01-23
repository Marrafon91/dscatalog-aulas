package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Service
public class CityService {

    @Autowired
    CityRepository repository;

   @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> list = repository.findAll(by("name"));
       return list.stream().map(CityDTO::new).toList();
    }
}
