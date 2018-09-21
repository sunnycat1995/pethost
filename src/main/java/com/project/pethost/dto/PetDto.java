package com.project.pethost.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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

    public PetDto(final String name,
                  final LocalDate birthdate,
                  final AnimalCategoryDto category,
                  final String description,
                  final String avatarUrl) {
        this.name = name;
        this.birthdate = birthdate;
        this.category = category;
        this.description = description;
        this.avatarUrl = avatarUrl;
    }
}
