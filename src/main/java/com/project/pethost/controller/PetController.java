package com.project.pethost.controller;


import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.exception.AnimalCategoryNotFoundException;
import com.project.pethost.exception.UserNotFoundException;
import com.project.pethost.repository.AnimalCategoryRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/pethost")
public class PetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AnimalCategoryRepository animalCategoryRepository;

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
                pet.setKeeper(user);
            } else throw new UserNotFoundException("You are not login. Please, login the page.");

            pet.setName(name);
            pet.setBirthdate(birthdate);
            pet.setDescription(description);
            pet.setRating(0.0);

            final Iterable<AnimalCategoryDbo> iterable = animalCategoryRepository.findAll();

            final List<AnimalCategoryDbo> animalCategories =
                    StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());

            final Optional<AnimalCategoryDbo> animalCategory =
                    animalCategories.stream().filter(category -> category.getId().equals(categoryId)).findAny();

            animalCategory.ifPresent(pet::setCategory);

            if (!animalCategory.isPresent()) {
                throw new AnimalCategoryNotFoundException("Not found animal category with id = " + categoryId);
            }

            petRepository.save(pet);
            return "Saved";
        }
        return "Not saved";
    }


    @RequestMapping(value = "/searchWaitingPetsByCategories", method = RequestMethod.GET)
    public @ResponseBody String searchWaitingPetsByCategories() {
        return "Returned all waiting orders filtered by categories";
    }

    @RequestMapping("/pets")
    public @ResponseBody Iterable<PetDbo> getAllPets(final Model model, @AuthenticationPrincipal final Principal principal) {
        final String userName = principal.getName();
        final UserDbo currentUser = userRepository.findByEmail(userName);
        model.addAttribute("currentUser", currentUser);

        return petRepository.findAllByOwner(currentUser);
    }
}
