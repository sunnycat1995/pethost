package com.project.pethost.repository;

import com.project.pethost.dbo.location.CityDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<CityDbo, Integer> {
    List<CityDbo> findAllByName(String name);
}
