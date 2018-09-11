package com.project.pethost.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
