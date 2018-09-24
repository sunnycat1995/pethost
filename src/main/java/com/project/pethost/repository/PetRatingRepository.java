package com.project.pethost.repository;

import com.project.pethost.dbo.rating.PetRatingDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetRatingRepository extends CrudRepository<PetRatingDbo, Long> {
    Optional<PetRatingDbo> findById(final Long id);
}
