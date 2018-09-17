package com.project.pethost.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@Getter
@Table(name = "user_role_type")
public enum UserRoleTypeDbo {
    ROLE_SUPER_ADMIN(1, "ROLE_SUPER_ADMIN"),
    ROLE_ADMIN(2, "ROLE_ADMIN"),
    ROLE_USER(3, "ROLE_USER");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;
}

