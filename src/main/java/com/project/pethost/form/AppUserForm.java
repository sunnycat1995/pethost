package com.project.pethost.form;

import com.project.pethost.dbo.AnimalCategoryDbo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserForm {
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String confirmPassword;
    private String countryCode;
    private List<AnimalCategoryDbo> animalPreferences;
}
