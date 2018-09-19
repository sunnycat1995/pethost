package com.project.pethost.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PetDto {
    private Long id;
    private String name;
    private LocalDate birthdate;

    private AnimalCategoryDto category;

    private UserDto owner;
    private UserDto keeper;

    private String description;

    private String avatarUrl;

    private LocalDateTime createdDate;
}
