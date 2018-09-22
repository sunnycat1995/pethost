package com.project.pethost.controller;


import com.project.pethost.converter.dbodto.PetDboDtoConverter;
import com.project.pethost.converter.dbodto.UserDboDtoConverter;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.PetDto;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.form.PetCreationForm;
import com.project.pethost.repository.PetRatingRepository;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.repository.UserRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class PetController {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetRatingRepository petRatingRepository;
    private final RatingDboFactory ratingDboFactory;
    private final PetDboDtoConverter petDboDtoConverter;
    private PetCreationValidator petCreationValidator;
    private UserDboDtoConverter userDboDtoConverter;

    private DataService dataService;


    @Autowired
    public PetController(final UserRepository userRepository,
                         final PetRepository petRepository,
                         final PetRatingRepository petRatingRepository,
                         final RatingDboFactory ratingDboFactory,
                         final PetDboDtoConverter petDboDtoConverter,
                         final PetCreationValidator petCreationValidator,
                         final UserDboDtoConverter userDboDtoConverter,
                         final DataService dataService) {
        this.userRepository = userRepository;
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

    /*@GetMapping(path = "/createPet") // Map ONLY GET Requests
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
    }*/

    @RequestMapping(value = "/searchWaitingOrdersByAnimalCategories", method = RequestMethod.GET)
    public @ResponseBody String searchWaitingOrdersByAnimalCategories() {
        return "Returned all waiting orders filtered by categories";
    }

    @GetMapping("/mypets")
    public String getAllPets(final Model model, @AuthenticationPrincipal final Principal principal) {
        final UserDbo currentUser = getCurrentUser(principal);
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
                          final RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal final Principal principal) {
        final PetDto pet = form.getPets().get(0);

        // Validate result
        if (result.hasErrors()) {
            return "createPetFormPage";
        }
        final UserDbo currentUser = getCurrentUser(principal);
        final PetDto petDto = new PetDto(pet.getName(), pet.getBirthdate(), pet.getCategory(), pet.getDescription(),
                                         pet.getAvatarUrl());
        final PetDbo petDbo = petDboDtoConverter.convertToDbo(petDto);
        petDbo.setOwner(currentUser);
        petDbo.setCreatedDate(LocalDateTime.now());

        petRepository.save(petDbo);

        return "redirect:mypets";
    }

    private UserDbo getCurrentUser(final @AuthenticationPrincipal Principal principal) {
        final String userName = principal.getName();
        return userRepository.findByEmail(userName);
    }
}
