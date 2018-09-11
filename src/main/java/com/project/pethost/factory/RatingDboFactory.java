package com.project.pethost.factory;

import com.project.pethost.dbo.RatingDbo;
import com.project.pethost.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RatingDboFactory {
    public RatingDbo createRatingDbo(final @RequestParam String email, final UserRepository userRepository) {
        final RatingDbo ratingDbo = new RatingDbo();
        ratingDbo.setId(userRepository.findByEmail(email).getId());
        ratingDbo.setCounter(0L);
        ratingDbo.setRating(0.0);
        return ratingDbo;
    }
}
