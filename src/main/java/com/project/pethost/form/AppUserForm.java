package com.project.pethost.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String[] animalPreferences;
}
