package com.devsuperior.bds02.repositories;

import com.devsuperior.bds02.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

// Link JPA Query Methods
//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

public interface CityRepository extends JpaRepository<City, Long> {

}
