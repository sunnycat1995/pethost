package com.project.pethost.repository;

import com.project.pethost.dbo.UserDbo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDbo, Long> {
    UserDbo findByEmail(final String email);
}
