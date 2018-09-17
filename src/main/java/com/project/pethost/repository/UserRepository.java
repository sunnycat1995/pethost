package com.project.pethost.repository;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.UserDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDbo, Long> {
    UserDbo findByEmail(final String email);
    List<UserDbo> findByNameContains(final String name);
    List<UserDbo> findAllByAnimalCategoryPreference(final AnimalCategoryDbo animalCategory);
    /*@Query("SELECT coalesce(max(u.id), 0) FROM user u")
    Long getMaxId();*/
}
