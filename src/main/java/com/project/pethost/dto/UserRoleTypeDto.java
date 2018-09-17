package com.project.pethost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum UserRoleTypeDto {
    ROLE_SUPER_ADMIN(1, "ROLE_SUPER_ADMIN"),

    ROLE_ADMIN(2, "ROLE_ADMIN"),

    ROLE_USER(3, "ROLE_USER");

    private Integer id;
    private String role;
}
