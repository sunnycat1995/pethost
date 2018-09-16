package com.project.pethost.dto.rating;

import lombok.Data;

@Data
public abstract class RatingDto {
    private Long id;
    private Double rating;
    private Long counter;
}
