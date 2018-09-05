package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "pet")
public class PetDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate birthdate;

    private AnimalCategoryDbo category;

    @OneToOne(targetEntity = UserDbo.class)
    private UserDbo owner;
    @OneToOne(targetEntity = UserDbo.class)
    private UserDbo keeper;

    private Double rating;

    private String description;
}
