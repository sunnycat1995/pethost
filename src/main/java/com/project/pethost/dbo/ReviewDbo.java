package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "review")
public class ReviewDbo {
    private Long id;
    private OrderDbo order;
    private String review;
    private int keeperRating;
    private int petRating;
}
