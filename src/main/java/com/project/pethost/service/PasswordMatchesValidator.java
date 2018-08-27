package com.project.pethost.service;

import com.project.pethost.model.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context){
        final Person user = (Person) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
