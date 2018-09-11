package com.project.pethost.repository;

import com.project.pethost.dbo.PetDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<PetDbo, Long> {
    List<PetDbo> findAllByCategory(final Integer categoryId);
}
