package com.project.pethost.validator;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDbo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final UserDbo user = (UserDbo) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getName().length() < 6 || user.getName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (personRepository.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
