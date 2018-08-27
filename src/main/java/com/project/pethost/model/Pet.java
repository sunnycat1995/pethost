package com.project.pethost.model;

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
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate birthdate;

    private AnimalCategory category;

    @OneToOne(targetEntity = Person.class)
    private Person owner;
    @OneToOne(targetEntity = Person.class)
    private Person keeper;

    private Double rating;
}
