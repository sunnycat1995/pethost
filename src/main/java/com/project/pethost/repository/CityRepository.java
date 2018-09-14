package com.project.pethost.repository;

import com.project.pethost.dbo.CityDbo;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<CityDbo, Integer> {
}
