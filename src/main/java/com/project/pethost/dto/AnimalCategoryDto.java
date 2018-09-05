package com.project.pethost.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AnimalCategoryDto {
    CAT(1, "cat"),
    DOG(2, "dog"),
    FISH(3, "fish"),
    BIRD(4, "bird"),
    REPTILE(5, "reptile"),
    RODENT(6, "rodent"),
    EXOTICS(7, "exotics");

    private Integer id;
    private String name;
}
