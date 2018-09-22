package com.project.pethost.repository;

import com.project.pethost.dbo.AnimalCategoryDbo;
import org.springframework.data.repository.CrudRepository;

public interface AnimalCategoryRepository extends CrudRepository<AnimalCategoryDbo, Integer>{
    AnimalCategoryDbo findByCategory(final String category);
}
