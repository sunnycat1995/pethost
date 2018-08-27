package com.project.pethost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pethost.service.PasswordMatches;
import com.project.pethost.service.ValidEmail;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "person")
@PasswordMatches
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    private LocalDate birthdate;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> phone;

    @OneToOne(cascade = CascadeType.ALL)
    private District district;

    @OneToOne(cascade = CascadeType.ALL)
    private City city;

    private String address;

    private Double rating;

    @Column
    @ElementCollection(targetClass = String.class)
    @JsonIgnore
    private List<AnimalCategory> animalCategoryPreference;
    /*@OneToMany(cascade = CascadeType.ALL)
    private List<Pet> pets;*/
}
