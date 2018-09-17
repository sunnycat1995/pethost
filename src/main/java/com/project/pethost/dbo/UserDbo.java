package com.project.pethost.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.dbo.location.DistrictDbo;
import com.project.pethost.validator.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
//@PasswordMatches
@NoArgsConstructor
public class UserDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String matchingPassword;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private GenderDbo gender;

    private LocalDate birthdate;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @Column
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_phone", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> phone;

    @OneToOne(cascade = CascadeType.ALL)
    private DistrictDbo district;

    @OneToOne(cascade = CascadeType.ALL)
    private CityDbo city;

    private String address;

    private Double rating;

    private Boolean enabled;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    //@JsonIgnore
    @CollectionTable(name = "user_animal_category", joinColumns = @JoinColumn(name = "user_id"))
    private Set<AnimalCategoryDbo> animalCategoryPreference;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonIgnore
    private Set<PetDbo> pets;

    public UserDbo(@NotNull @NotEmpty final String password,
                   @NotEmpty final String name,
                   @NotEmpty final String surname,
                   @NotNull @NotEmpty final String email) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
