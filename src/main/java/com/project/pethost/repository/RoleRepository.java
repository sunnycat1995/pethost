package com.project.pethost.repository;

import com.project.pethost.dbo.RoleDbo;
import com.project.pethost.dbo.UserRoleTypeDbo;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleDbo, Long> {
    RoleDbo findAllById(final Long id);
    RoleDbo findByRole(final UserRoleTypeDbo role);
}
