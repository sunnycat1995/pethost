package com.project.pethost.dto;

import com.project.pethost.service.PasswordMatches;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@PasswordMatches
public class UserDto {
    private Long id;

    private String password;

    private String matchingPassword;

    private String name;

    private String surname;
    private String patronymic;

    private GenderDto gender;

    private LocalDate birthdate;

    private String email;

    private List<String> phone;

    private DistrictDto district;

    private CityDto city;

    private String address;

    private Double rating;

    private Boolean enabled;

    private List<AnimalCategoryDto> animalCategoryPreference;
    private List<PetDto> pets;
}
