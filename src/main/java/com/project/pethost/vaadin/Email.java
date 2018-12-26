package com.project.pethost.vaadin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Email {
    private String name;
    private String message;
    private ArrayList<String> recipients;
    //private LocalDate date;
}
