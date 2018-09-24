package com.project.pethost.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private OrderDto order;
    private String review;
    private Integer keeperRating;
    private Integer petRating;
}
