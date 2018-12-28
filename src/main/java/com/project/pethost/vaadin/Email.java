package com.project.pethost.vaadin;

import java.time.LocalDate;
import java.util.ArrayList;

public class Email {
    private String name;
    private String message;
    private ArrayList<String> recipients;
    private LocalDate date;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(final ArrayList<String> recipients) {
        this.recipients = recipients;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Email(final String name, final String message, final ArrayList<String> recipients, final LocalDate date) {
        this.name = name;
        this.message = message;
        this.recipients = recipients;
        this.date = date;
    }


}
