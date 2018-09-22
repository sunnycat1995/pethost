package com.project.pethost.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetDto {
    private Long id;
    private String name;
    private String birthdate;
    private String category;
    private String description;
    private String avatarUrl;

    public PetDto (final String name,
                  final String birthdate,
                  final String category,
                  final String description,
                  final String avatarUrl) {
        this.name = name;
        this.birthdate = birthdate;
        this.category = category;
        this.description = description;
        this.avatarUrl = avatarUrl;
    }
}
