package com.project.pethost.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.pethost.dto.location.CityDto;
import com.project.pethost.dto.location.DistrictDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
//@PasswordMatches
public class UserDto {
    private Long id;

    private String password;

    //private String confirmPassword;

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

    private Boolean enabled;

    private List<AnimalCategoryDto> animalCategoryPreference;
    private List<PetDto> pets;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
}
