package com.project.pethost.controller;

import com.project.pethost.converter.GenderEnumConverter;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.GenderDbo;
import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.exception.AnimalCategoryNotFoundException;
import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.repository.AnimalCategoryRepository;
import com.project.pethost.repository.OrderRepository;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.repository.UserRepository;
import com.project.pethost.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/pethost")
public class MainController extends WebMvcConfigurationSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired // This means to get the bean called personRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository personRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private AnimalCategoryRepository animalCategoryRepository;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(final Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(final Principal principal) {
        if (principal != null) {
            return "userInfoPage";
        }
        return "loginPage";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "loginPage";
    }

    @RequestMapping(value = "/userAccountInfo", method = RequestMethod.GET)
    public String userInfo(final Model model, final Principal principal) {
        if (principal != null) {
            final String userName = principal.getName();

            System.out.println("User Name: " + userName);

            final User loginedUser = (User) ((Authentication) principal).getPrincipal();

            model.addAttribute("userInfo", loginedUser.toString());

            return "userInfoPage";
        }
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(final Model model) {
        model.addAttribute("title", "Logout");
        SecurityContextHolder.clearContext();
        return "logoutSuccessfulPage";
    }


    @GetMapping(path = "/register") // Map ONLY GET Requests
    public @ResponseBody String addNewPerson(@RequestParam final String name,
                                             @RequestParam final String surname,
                                             @RequestParam(required = false) final String patronymic,
                                             @RequestParam(name = "gender") GenderDbo gender,

                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             @RequestParam(required = false) final LocalDate birthdate,

                                             @RequestParam final String email,
                                             @RequestParam(required = false) final List<String> phone,
                                             @RequestParam(required = false) final Long districtId,
                                             @RequestParam(required = false) final Long cityId,
                                             @RequestParam(required = false) final String address,
                                             @RequestParam final String password,
                                             @RequestParam final String matchingPassword
                                             //@ModelAttribute final List<AnimalCategory> animalCategories
                                            ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request


        final UserDbo p = new UserDbo();
        p.setName(name);
        p.setSurname(surname);
        p.setPatronymic(patronymic);
        p.setGender(gender);
        p.setBirthdate(birthdate);
        p.setEmail(email);
        p.setPhone(phone);
        p.setPassword(password);
        p.setMatchingPassword(matchingPassword);
        //p.setDistrict();
        //p.setCityId();
        p.setAddress(address);
        //personRepository.save(p);
        try {
            personService.registerNewUserAccount(p);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return "Saved";
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final Model model, final String error, final String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }*/

    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new GenderEnumConverter());
        return f;
    }

    @GetMapping(path = "/createPet") // Map ONLY GET Requests
    public @ResponseBody String addNewPet(@RequestParam String name, @RequestParam Integer categoryId)
            throws AnimalCategoryNotFoundException {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        final PetDbo pet = new PetDbo();
        pet.setName(name);

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


    @GetMapping(path = "/users")
    public @ResponseBody Iterable<UserDbo> getAllUsers() {
        return personRepository.findAll();
    }

    @GetMapping(path = "/orders")
    public @ResponseBody Iterable<OrderDbo> getAllOrders() {
        return orderRepository.findAll();
    }

    /*
        /admin

        /users

        /orders

        /waitingOrders

        /searchWaitingPetsByCategories

        /searchPersonsByInterests

        /register(create new User)

        /login

        /createPet

        !!!
        /authorization(for order)
        login
        password

        /sendOrder

        /changeOrderStatus

        /createReview(only by ownerId about keeperId)

        /recalculateRating

        /error

        /userAccountInfo
     */
}

