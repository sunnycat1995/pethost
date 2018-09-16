package com.project.pethost.factory;

import com.project.pethost.constant.RatingConstant;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.rating.KeeperRatingDbo;
import com.project.pethost.dbo.rating.PetRatingDbo;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class RatingDboFactory {
    private final RatingConstant ratingConstant;

    @Autowired
    public RatingDboFactory(final RatingConstant ratingConstant) {
        this.ratingConstant = ratingConstant;
    }

    public KeeperRatingDbo createRatingDbo(final @RequestParam String email, final UserRepository userRepository) {
        final KeeperRatingDbo ratingDbo = new KeeperRatingDbo();
        ratingDbo.setId(userRepository.findByEmail(email).getId());
        ratingDbo.setCounter(ratingConstant.getCounter());
        ratingDbo.setRating(ratingConstant.getRating());
        return ratingDbo;
    }

    public PetRatingDbo createRatingDbo(final @RequestParam Long id, final PetRepository petRepository) {
        final PetRatingDbo ratingDbo = new PetRatingDbo();
        final Optional<PetDbo> petDbo = petRepository.findById(id);
        petDbo.ifPresent(r -> {
            ratingDbo.setId(r.getId());
        });
        ratingDbo.setCounter(ratingConstant.getCounter());
        ratingDbo.setRating(ratingConstant.getRating());
        return ratingDbo;
    }
}
