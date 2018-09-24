package com.project.pethost.repository;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends CrudRepository<PetDbo, Long> {
    List<PetDbo> findAllByCategory(final AnimalCategoryDbo animalCategory);

    List<PetDbo> findAllByOwner(final UserDbo user);

    Optional<PetDbo> findById(final Long id);

    List<PetDbo> findAllByNameAndCategoryAndCreatedDateEquals(final String name,
                                                              final AnimalCategoryDbo animalCategoryDbo,
                                                              final LocalDateTime createdDate);
    List<PetDbo> findAllByOwnerAndAndProcessed(final UserDbo owner, boolean isProcessed);
}