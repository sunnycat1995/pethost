package com.project.pethost.exception;

public class EmailNotFoundException extends Throwable {
    public EmailNotFoundException(final String message) {
        super(message);
    }
}
