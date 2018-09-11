package com.project.pethost.dbo;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_category")
@NoArgsConstructor
public class AnimalCategoryDbo {
    /*CAT(1, "cat"),
    DOG(2, "dog"),
    FISH(3, "fish"),
    BIRD(4, "bird"),
    REPTILE(5, "reptile"),
    RODENT(6, "rodent"),
    EXOTICS(7, "exotics");*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
}
