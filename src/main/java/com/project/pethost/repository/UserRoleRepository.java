package com.project.pethost.repository;

import com.project.pethost.dbo.UserRoleDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRoleDbo, Long> {
    List<UserRoleDbo> findAllById(final Long userId);
}
