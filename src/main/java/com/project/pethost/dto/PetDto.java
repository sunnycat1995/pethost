package com.project.pethost.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PetDto {
    private Long id;
    private String name;
    private LocalDate birthdate;

    private AnimalCategoryDto category;

    private UserDto owner;
    private UserDto keeper;

    private Double rating;

    private String description;
}
