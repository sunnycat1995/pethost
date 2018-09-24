package com.project.pethost.form;

import lombok.Data;

@Data
public class OrderCreationForm {
    private Long petId;
    private String comments;
}