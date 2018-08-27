package com.project.pethost.repository;

import com.project.pethost.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByEmail(final String email);
}
