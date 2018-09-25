package com.project.pethost.controller;

import com.project.pethost.converter.dbodto.OrderDboDtoConverter;
import com.project.pethost.dbo.ReviewDbo;
import com.project.pethost.dbo.rating.KeeperRatingDbo;
import com.project.pethost.dbo.rating.PetRatingDbo;
import com.project.pethost.dto.ReviewDto;
import com.project.pethost.repository.KeeperRatingRepository;
import com.project.pethost.repository.PetRatingRepository;
import com.project.pethost.repository.ReviewRepository;
import com.project.pethost.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class MainController extends WebMvcConfigurationSupport {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private final ReviewRepository reviewRepository;
    private final KeeperRatingRepository keeperRatingRepository;
    private final PetRatingRepository petRatingRepository;
    private final DataService dataService;
    private final OrderDboDtoConverter orderDboDtoConverter;

    @Autowired
    public MainController(final ReviewRepository reviewRepository,
                          final KeeperRatingRepository keeperRatingRepository,
                          final PetRatingRepository petRatingRepository,
                          final DataService dataService,
                          final OrderDboDtoConverter orderDboDtoConverter) {
        this.reviewRepository = reviewRepository;
        this.keeperRatingRepository = keeperRatingRepository;
        this.petRatingRepository = petRatingRepository;
        this.dataService = dataService;
        this.orderDboDtoConverter = orderDboDtoConverter;
    }

    @RequestMapping(value = {"", "/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(final Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "Welcome to the Pethost page!");
        model.addAttribute("content", "Content text");
        return "welcomePage";
    }

    @RequestMapping(value = "/createReview", method = RequestMethod.GET)
    public String createReview(final Model model, @AuthenticationPrincipal final Principal principal) {
        model.addAttribute("form", new ReviewDto());
        return "reviews/createReviewPage";
    }

    @RequestMapping(value = "/createReview", method = RequestMethod.POST)
    public String saveReview(@ModelAttribute @Valid final ReviewDto form,
                             final BindingResult result,
                             @AuthenticationPrincipal final Principal principal) {
        final ReviewDbo reviewDbo = new ReviewDbo();
        reviewDbo.setReview(form.getReview());
        reviewDbo.setKeeperRating(form.getKeeperRating());
        reviewDbo.setPetRating(form.getPetRating());
        reviewDbo.setOrder(orderDboDtoConverter.convertToDbo(form.getOrder()));
        reviewRepository.save(reviewDbo);

        recalculateRating(form);

        return "reviews/createReviewPage";
    }


    private void recalculateRating(final ReviewDto form) {
        final Optional<KeeperRatingDbo> keeperRatingDbo = keeperRatingRepository.findById(form.getOrder().getOwnerId());
        if (keeperRatingDbo.isPresent()) {
            final KeeperRatingDbo ratingDbo = keeperRatingDbo.get();
            final Double newKeeperRating = dataService
                    .recalculateRating(ratingDbo.getRating(), ratingDbo.getCounter(), form.getKeeperRating());
            ratingDbo.setRating(newKeeperRating);
            ratingDbo.setCounter(ratingDbo.getCounter() + 1);
            keeperRatingRepository.save(ratingDbo);
        } else {
            final KeeperRatingDbo ratingDbo = new KeeperRatingDbo();
            ratingDbo.setCounter(1L);
            ratingDbo.setRating(Double.valueOf(form.getKeeperRating()));
            keeperRatingRepository.save(ratingDbo);
        }

        final Optional<PetRatingDbo> petRatingDbo =
                petRatingRepository.findById(Long.valueOf(form.getOrder().getPetId()));
        if (petRatingDbo.isPresent()) {
            final PetRatingDbo ratingDbo = petRatingDbo.get();
            final Double newPetRating = dataService
                    .recalculateRating(ratingDbo.getRating(), ratingDbo.getCounter(), form.getKeeperRating());
            ratingDbo.setRating(newPetRating);
            ratingDbo.setCounter(ratingDbo.getCounter() + 1);
            petRatingRepository.save(ratingDbo);
        } else {
            final PetRatingDbo ratingDbo = new PetRatingDbo();
            ratingDbo.setCounter(1L);
            ratingDbo.setRating(Double.valueOf(form.getPetRating()));
            petRatingRepository.save(ratingDbo);
        }
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody String error() { //(only by ownerId about keeperId)
        return "Error";
    }
}

