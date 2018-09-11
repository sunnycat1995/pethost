package com.project.pethost.dbo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "animal_category")
public enum AnimalCategoryDbo {
    CAT(1, "cat"),
    DOG(2, "dog"),
    FISH(3, "fish"),
    BIRD(4, "bird"),
    REPTILE(5, "reptile"),
    RODENT(6, "rodent"),
    EXOTICS(7, "exotics");

    @Id
    private Integer id;
    private String category;
}
