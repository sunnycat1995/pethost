package com.project.pethost.validator;

import com.project.pethost.form.PetCreationForm;
import com.project.pethost.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PetCreationValidator implements Validator {

    private final PetRepository petRepository;

    @Autowired
    public PetCreationValidator(final PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz == PetCreationForm.class;
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final PetCreationForm petCreationForm = (PetCreationForm) target;

        System.out.println(petCreationForm);
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "not.empty.app.userform.firstname");
        /*if (!this.emailValidator.isValid(petCreationForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "pattern.app.userform.email");
        } else if (petCreationForm.getUserId() == null) {
            final UserDbo dbUser = appUserDAO.findByEmail(petCreationForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "duplicate.userform.email");
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            final UserDbo dbUser = appUserDAO.findByEmail(petCreationForm.getUserName());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("userName", "Duplicate.petCreationForm.userName");
            }
        }

        if (!errors.hasErrors()) {
            if (!petCreationForm.getConfirmPassword().equals(petCreationForm.getPassword())) {
                errors.rejectValue("confirmPassword", "match.app.userform.confirm.password");
            }
        }*/
    }
}