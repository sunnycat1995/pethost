package com.project.pethost.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pethost.constant.Constants;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.dbo.location.DistrictDbo;
import com.project.pethost.validator.ValidEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
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
    private String confirmPassword;

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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictDbo district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CityDbo city;

    private String address;

    private Boolean enabled;

    @Column
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_animal_category", joinColumns = @JoinColumn(name = "user_id"),
            indexes = {@Index(name = "UserAnimalPreferencesAttributes",
                    columnList = "user_id, animal_category_preference_id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "animal_category_preference_id"}))
    private Set<AnimalCategoryDbo> animalCategoryPreference;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
    private Set<PetDbo> pets;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "keeper")
    @JsonIgnore
    private Set<PetDbo> pets1;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    public UserDbo(@NotNull @NotEmpty final String password,
                   @NotEmpty final String name,
                   @NotEmpty final String surname,
                   @NotNull @NotEmpty final String email,
                   final String gender,
                   final CityDbo cityDbo,
                   final Set<AnimalCategoryDbo> animalCategoryPreference) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = GenderDbo.valueOf(gender);
        this.enabled = true;
        this.createdDate = LocalDateTime.now();
        this.city = cityDbo;
        this.animalCategoryPreference = animalCategoryPreference;
    }
}
