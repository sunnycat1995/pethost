package com.project.pethost.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
