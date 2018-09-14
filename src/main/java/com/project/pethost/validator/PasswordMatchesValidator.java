package com.project.pethost.validator;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.service.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context){
        final UserDbo user = (UserDbo) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
