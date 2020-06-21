package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    long countByCity(String city);

    long countByName(String name);

}
