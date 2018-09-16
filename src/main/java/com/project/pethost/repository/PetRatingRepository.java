package com.project.pethost.repository;

import com.project.pethost.dbo.rating.PetRatingDbo;
import org.springframework.data.repository.CrudRepository;

public interface PetRatingRepository extends CrudRepository<PetRatingDbo, Long> {
}
