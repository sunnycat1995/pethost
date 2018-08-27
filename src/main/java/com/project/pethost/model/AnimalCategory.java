package com.project.pethost.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "animal_category")
public enum AnimalCategory {
    CAT(1, "cat"),
    DOG(2, "dog"),
    FISH(3, "fish"),
    BIRD(4, "bird"),
    REPTILE(5, "reptile"),
    RODENT(6, "rodent"),
    EXOTICS(7, "exotics");

    @Id
    private Integer id;
    private String name;
}
