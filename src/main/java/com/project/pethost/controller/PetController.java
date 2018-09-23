package com.project.pethost.controller;


import com.project.pethost.converter.dbodto.PetDboDtoConverter;
import com.project.pethost.converter.dbodto.UserDboDtoConverter;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.PetDto;
import com.project.pethost.exception.UserNotFoundException;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.form.PetCreationForm;
import com.project.pethost.repository.PetRatingRepository;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.service.DataService;
import com.project.pethost.validator.PetCreationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class PetController {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final PetRepository petRepository;
    private final PetRatingRepository petRatingRepository;
    private final RatingDboFactory ratingDboFactory;
    private final PetDboDtoConverter petDboDtoConverter;
    private PetCreationValidator petCreationValidator;
    private UserDboDtoConverter userDboDtoConverter;

    private DataService dataService;


    @Autowired
    public PetController(final PetRepository petRepository,
                         final PetRatingRepository petRatingRepository,
                         final RatingDboFactory ratingDboFactory,
                         final PetDboDtoConverter petDboDtoConverter,
                         final PetCreationValidator petCreationValidator,
                         final UserDboDtoConverter userDboDtoConverter,
                         final DataService dataService) {
        this.petRepository = petRepository;
        this.petRatingRepository = petRatingRepository;
        this.ratingDboFactory = ratingDboFactory;
        this.petDboDtoConverter = petDboDtoConverter;
        this.petCreationValidator = petCreationValidator;
        this.userDboDtoConverter = userDboDtoConverter;
        this.dataService = dataService;
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(final WebDataBinder dataBinder) {
        // Form target
        final Object target = dataBinder.getTarget();
        LOGGER.info("Target=" + target);
        if (target != null) {
            if (target.getClass() == PetCreationForm.class) {
                dataBinder.setValidator(petCreationValidator);
            }
        }
    }

    @RequestMapping(value = "/searchWaitingOrdersByAnimalCategories", method = RequestMethod.GET)
    public @ResponseBody String searchWaitingOrdersByAnimalCategories() {
        return "Returned all waiting orders filtered by categories";
    }

    @GetMapping("/mypets")
    public String getAllPets(final Model model, @AuthenticationPrincipal final Principal principal) {
        final UserDbo currentUser = dataService.getCurrentUser(principal);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("pets", petRepository.findAllByOwner(currentUser));
        return "pets/myPetsPage";
    }

    @GetMapping("/createPet")
    public String showCreateForm(final Model model, @AuthenticationPrincipal final Principal principal) {
        final PetCreationForm petsForm = new PetCreationForm();
        petsForm.addPet(new PetDto());

        model.addAttribute("form", petsForm);
        final List<AnimalCategoryDbo> animalCategories = dataService.animalCategories();
        model.addAttribute("animalCategories", animalCategories);
        return "pets/createPetFormPage";
    }

    @PostMapping("/createPet")
    public String savePet(@ModelAttribute @Valid final PetCreationForm form,
                          final BindingResult result,
                          @AuthenticationPrincipal final Principal principal) throws UserNotFoundException {
        final PetDto pet = form.getPets().get(0);

        // Validate result
        if (result.hasErrors()) {
            return "pets/createPetFormPage";
        }
        final UserDbo currentUser = dataService.getCurrentUser(principal);
        final PetDto petDto = new PetDto(pet.getName(), pet.getBirthdate(), pet.getCategory(), pet.getDescription(),
                                         pet.getAvatarUrl());
        final PetDbo petDbo = petDboDtoConverter.convertToDbo(petDto);

        if (currentUser != null) {
            petDbo.setOwner(currentUser);
        } else {
            final String message = "You are not logged in. Please log in and try again.";
            throw new UserNotFoundException(message);
        }

        petDbo.setOwner(currentUser);
        petDbo.setCreatedDate(LocalDateTime.now());

        petRepository.save(petDbo);

        return "redirect:mypets";
    }
}
