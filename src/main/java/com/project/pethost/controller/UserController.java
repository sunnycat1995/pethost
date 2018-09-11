package com.project.pethost.controller;

import com.project.pethost.converter.GenderEnumConverter;
import com.project.pethost.converter.UserDboDtoConverter;
import com.project.pethost.dbo.GenderDbo;
import com.project.pethost.dbo.RatingDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.UserDto;
import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.repository.RatingRepository;
import com.project.pethost.repository.UserRepository;
import com.project.pethost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "/pethost")
public class UserController extends WebMvcConfigurationSupport {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDboDtoConverter userDboDtoConverter;
    private final RatingRepository ratingRepository;
    private final RatingDboFactory ratingDboFactory;

    @Autowired
    public UserController(final UserRepository userRepository,
                          final UserService userService,
                          final UserDboDtoConverter userDboDtoConverter,
                          final RatingRepository ratingRepository,
                          final RatingDboFactory ratingDboFactory) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDboDtoConverter = userDboDtoConverter;
        this.ratingRepository = ratingRepository;
        this.ratingDboFactory = ratingDboFactory;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(final WebRequest request, final Model model) {
        final UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signupPage";
    }

    /*@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto accountDto,
            BindingResult result,
            WebRequest request,
            Errors errors) {

        UserDbo registered = new UserDbo();
        if (!result.hasErrors()) {
            createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("signup", "user", accountDto);
        }
        else {
            return new ModelAndView("userInfoPage", "user", accountDto);
        }
    }*/

    private UserDbo createUserAccount(final UserDto accountDto, final BindingResult result) {

        UserDbo userDbo = userDboDtoConverter.convertToDbo(accountDto);
        userDbo = userRepository.save(userDbo);

        return userDbo;
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
        //userRepository.save(p);
        try {
            userService.registerNewUserAccount(p);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        final RatingDbo ratingDbo = ratingDboFactory.createRatingDbo(email, userRepository);
        ratingRepository.save(ratingDbo);
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

    @GetMapping(path = "/users")
    public @ResponseBody Iterable<UserDbo> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/searchUsersByAnimalPreferences", method = RequestMethod.GET)
    public @ResponseBody String searchUsersByAnimalPreferences() {
        return "Returned all users filtered by animal categories preferences";
    }
}
