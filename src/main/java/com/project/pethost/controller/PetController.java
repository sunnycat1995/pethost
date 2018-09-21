package com.project.pethost.controller;


import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.rating.PetRatingDbo;
import com.project.pethost.exception.AnimalCategoryNotFoundException;
import com.project.pethost.exception.UserNotFoundException;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.repository.AnimalCategoryRepository;
import com.project.pethost.repository.PetRatingRepository;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/pethost")
public class PetController {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AnimalCategoryRepository animalCategoryRepository;
    private final PetRatingRepository petRatingRepository;
    private final RatingDboFactory ratingDboFactory;


    @Autowired
    public PetController(final UserRepository userRepository,
                         final PetRepository petRepository,
                         final AnimalCategoryRepository animalCategoryRepository,
                         final PetRatingRepository petRatingRepository,
                         final RatingDboFactory ratingDboFactory) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.animalCategoryRepository = animalCategoryRepository;
        this.petRatingRepository = petRatingRepository;
        this.ratingDboFactory = ratingDboFactory;
    }

    @GetMapping(path = "/createPet") // Map ONLY GET Requests
    public @ResponseBody String addNewPet(@RequestParam String name,
                                          @RequestParam Integer categoryId,
                                          @RequestParam(required = false) LocalDate birthdate,
                                          @RequestParam(required = false) String description,
                                          final Principal principal)
            throws AnimalCategoryNotFoundException, UserNotFoundException {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        final PetDbo pet = new PetDbo();
        if (principal != null) {
            final String userName = principal.getName();
            final UserDbo user = userRepository.findByEmail(userName);
            if (user != null) {
                pet.setOwner(user);
            } else {
                final String message = "You are not logged in. Please log in and try again.";
                throw new UserNotFoundException(message);
            }

            pet.setName(name);
            pet.setBirthdate(birthdate);
            pet.setDescription(description);
            pet.setCreatedDate(LocalDateTime.now());

            final Iterable<AnimalCategoryDbo> iterable = animalCategoryRepository.findAll();

            final List<AnimalCategoryDbo> animalCategories =
                    StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());

            final Optional<AnimalCategoryDbo> animalCategory =
                    animalCategories.stream().filter(category -> category.getId().equals(categoryId)).findAny();

            animalCategory.ifPresent(pet::setCategory);

            if (!animalCategory.isPresent()) {
                final String message = "Not found animal category with id = " + categoryId;
                LOGGER.log(Level.SEVERE, message);
                throw new AnimalCategoryNotFoundException(message);
            }

            petRepository.save(pet);

            final List<PetDbo> pets =
                    petRepository.findAllByNameAndCategoryAndCreatedDateEquals(name,
                                                                               animalCategory.get(),
                                                                               pet.getCreatedDate());
            if (!pets.isEmpty()) {
                final PetRatingDbo petRatingDbo =
                        ratingDboFactory.createRatingDbo(pets.get(0).getId(), petRepository);
                petRatingRepository.save(petRatingDbo);
            }

            return "Saved";
        }
        return "Not saved";
    }

    @RequestMapping(value = "/searchWaitingOrdersByAnimalCategories", method = RequestMethod.GET)
    public @ResponseBody String searchWaitingOrdersByAnimalCategories() {
        return "Returned all waiting orders filtered by categories";
    }

    @GetMapping("/mypets")
    public String getAllPets(final Model model, @AuthenticationPrincipal final Principal principal) {
        final String userName = principal.getName();
        final UserDbo currentUser = userRepository.findByEmail(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("pets", petRepository.findAllByOwner(currentUser));
        return "myPetsPage";
    }

    /*@RequestMapping("/pets")
    public @ResponseBody UserDbo getAllPets(final Model model,
                                                     @AuthenticationPrincipal final Principal principal) {
        final String userName = principal.getName();
        final UserDbo currentUser = userRepository.findByEmail(userName);
        model.addAttribute("currentUser", currentUser);

        return userRepository.findByEmail(userName);
    }*/
}
