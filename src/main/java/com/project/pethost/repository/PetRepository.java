package com.project.pethost.repository;

import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<PetDbo, Long> {
    List<PetDbo> findAllByCategory(final Integer id);
    List<PetDbo> findAllByOwner(final UserDbo user);
}
