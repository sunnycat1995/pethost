package com.project.pethost.model;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "review")
public class Review {
    private Long id;
    private Order order;
    private String review;
    private int keeperRating;
    private int petRating;
}
