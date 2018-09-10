package com.project.pethost.repository;

import com.project.pethost.dbo.RoleDbo;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleDbo, Long> {
    RoleDbo findAllById(Long id);
}
