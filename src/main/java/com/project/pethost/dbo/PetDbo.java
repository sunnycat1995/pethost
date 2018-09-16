package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pet")
public class PetDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;

    @OneToOne(targetEntity = AnimalCategoryDbo.class)
    private AnimalCategoryDbo category;

    @OneToOne(targetEntity = UserDbo.class)
    private UserDbo owner;
    @OneToOne(targetEntity = UserDbo.class)
    private UserDbo keeper;

    private Double rating;

    private String description;

    private LocalDateTime createdDate;
}
