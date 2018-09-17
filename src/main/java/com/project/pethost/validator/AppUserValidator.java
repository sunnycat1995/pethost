package com.project.pethost.validator;

import com.project.pethost.AppUserForm;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AppUserValidator implements Validator {

    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();

    private final UserRepository appUserDAO;

    @Autowired
    public AppUserValidator(final UserRepository appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUserForm.class;
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final AppUserForm appUserForm = (AppUserForm) target;

        // Check the fields of AppUserForm.
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "not.empty.app.userform.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "not.empty.app.userform.firstname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "not.empty.app.userform.lastname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "not.empty.app.userform.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "not.empty.app.userform.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "not.empty.app.userform.confirm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "not.empty.app.user.form.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryCode", "not.empty.app.userform.city");

        if (!this.emailValidator.isValid(appUserForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "pattern.app.userform.email");
        } else if (appUserForm.getUserId() == null) {
            final UserDbo dbUser = appUserDAO.findByEmail(appUserForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "duplicate.userform.email");
            }
        }

        /*if (!errors.hasFieldErrors("userName")) {
            final UserDbo dbUser = appUserDAO.findByEmail(appUserForm.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.appUserForm.userName");
            }
        }*/

        if (!errors.hasErrors()) {
            if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
                errors.rejectValue("confirmPassword", "match.app.userform.confirm.password");
            }
        }
    }
}