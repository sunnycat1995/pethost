package com.project.pethost.dbo.rating;

import lombok.Data;

@Data
public abstract class RatingDbo {
    private Long id;
    private Double rating;
    private Long counter;
}
