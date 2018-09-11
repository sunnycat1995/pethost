package com.project.pethost.exception;

public class AnimalCategoryNotFoundException extends Throwable {
    public AnimalCategoryNotFoundException(final String message) {
        super(message);
    }
}
