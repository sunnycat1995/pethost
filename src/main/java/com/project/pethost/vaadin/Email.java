package com.project.pethost.vaadin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Email {
    private String name;
    private String message;
    private ArrayList<String> recipients;
    private LocalDate date;

    public Email(final String name, final String message, final ArrayList<String> recipients) {
        this.name = name;
        this.message = message;
        this.recipients = recipients;
    }
}
