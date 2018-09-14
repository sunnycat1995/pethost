package com.project.pethost.validator;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDbo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final UserDbo user = (UserDbo) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "not.empty");
        if (user.getName().length() < 6 || user.getName().length() > 32) {
            errors.rejectValue("username", "size.userform.username");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("username", "duplicate.userform.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "not.empty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "size.userform.password");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "diff.userform.password.confirm");
        }
    }
}
