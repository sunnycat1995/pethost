package com.project.pethost.constant;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public final class RatingConstant {
    private final Double rating;
    private final Long counter;

    public RatingConstant() {
        rating = 0.0;
        counter = 0L;
    }

    @Override
    public String toString() {
        return String.format("%1f", rating);
    }
}
