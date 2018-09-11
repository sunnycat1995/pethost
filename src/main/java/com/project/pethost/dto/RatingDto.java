package com.project.pethost.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long id;
    private Double rating;
    private Long counter;
}
