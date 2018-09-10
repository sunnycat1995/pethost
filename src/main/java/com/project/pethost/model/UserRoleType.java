package com.project.pethost.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@Getter
@Table(name = "user_role_type")
public enum UserRoleType {
    ROLE_SUPER_ADMIN(1, "ROLE_SUPER_ADMIN"),
    ROLE_ADMIN(2, "ROLE_ADMIN"),
    ROLE_USER(3, "ROLE_USER");

    @Id
    private Integer id;
    private String role;
}

