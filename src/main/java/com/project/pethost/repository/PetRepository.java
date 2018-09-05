package com.project.pethost.repository;

import com.project.pethost.dbo.PetDbo;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<PetDbo, Long> {
}
