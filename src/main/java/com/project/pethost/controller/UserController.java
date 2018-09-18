package com.project.pethost.controller;

import com.project.pethost.form.AppUserForm;
import com.project.pethost.converter.GenderEnumConverter;
import com.project.pethost.converter.UserDboDtoConverter;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.dto.UserDto;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.repository.CityRepository;
import com.project.pethost.repository.KeeperRatingRepository;
import com.project.pethost.repository.UserRepository;
import com.project.pethost.service.UserServiceImpl;
import com.project.pethost.util.EncryptedPasswordUtils;
import com.project.pethost.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class UserController extends WebMvcConfigurationSupport {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final UserDboDtoConverter userDboDtoConverter;
    private final KeeperRatingRepository ratingRepository;
    private final RatingDboFactory ratingDboFactory;
    private final AppUserValidator appUserValidator;

    private final CityRepository cityRepository;


    @Autowired
    public UserController(final UserRepository userRepository,
                          final UserServiceImpl userService,
                          final UserDboDtoConverter userDboDtoConverter,
                          final KeeperRatingRepository ratingRepository,
                          final RatingDboFactory ratingDboFactory,
                          final AppUserValidator appUserValidator,
                          final CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDboDtoConverter = userDboDtoConverter;
        this.ratingRepository = ratingRepository;
        this.ratingDboFactory = ratingDboFactory;
        this.appUserValidator = appUserValidator;
        this.cityRepository = cityRepository;
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(final WebDataBinder dataBinder) {
        // Form target
        final Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        LOGGER.info("Target=" + target);

        if (target.getClass() == AppUserForm.class) {
            dataBinder.setValidator(appUserValidator);
        }
        // ...
    }

    /*@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(final WebRequest request, final Model model) {
        final UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signupPage";
    }*/

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("userHome", "user", new UserDbo());
    }

    /*@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") final UserDto accountDto,
            final BindingResult result,
            final WebRequest request,
            final Errors errors,
            final ModelMap model) {

        if (!result.hasErrors()) {
            model.addAttribute("email", accountDto.getEmail());
            model.addAttribute("password", accountDto.getPassword());
            model.addAttribute("name", accountDto.getName());
            model.addAttribute("surname", accountDto.getSurname());
            model.addAttribute("gender", accountDto.getGender());
            //model.addAttribute("phone", accountDto.getPhone().get(0));
            model.addAttribute("birthdate", accountDto.getBirthdate());
            //createUserAccount(accountDto, result);
        }
        return new ModelAndView("signupPage", "user", accountDto);
    }*/

    /*@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registrationPage";
    }*/


    /*@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user") final UserDto userDto,
                         BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        createUserAccount(userDto, result);
        return "userInfoPage";
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

    /*
    http://localhost:8091/pethost/register?name=Petya&surname=Petrov&email=petya@gmail.com&gender=male&phone=1234567&phone=9876543&birthdate=1995-05-18&enabled=true&password=$2a$10$KuRL4rJZhZdVV4nYVcGOrONdjJ7Pc0gJgB3AcHsgfyzlcq5ifAorq&matchingPassword=$2a$10$KuRL4rJZhZdVV4nYVcGOrONdjJ7Pc0gJgB3AcHsgfyzlcq5ifAorq
    */
    /*@GetMapping(path = "/register") // Map ONLY GET Requests
    public @ResponseBody String addNewPerson(@RequestParam final String name,
                                             @RequestParam final String surname,
                                             @RequestParam(required = false) final String patronymic,
                                             //@RequestParam(name = "gender") GenderDbo gender,

                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             @RequestParam(required = false) final LocalDate birthdate,

                                             @RequestParam final String email,
                                             @RequestParam(required = false) final List<String> phone,
                                             @RequestParam(required = false) final Long districtId,
                                             @RequestParam(required = false) final Long cityId,
                                             @RequestParam(required = false) final String address,
                                             @RequestParam final String password,
                                             @RequestParam final String confirmPassword
                                             //@ModelAttribute final List<AnimalCategory> animalCategories
                                            ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request


        final UserDbo p = new UserDbo();
        p.setName(name);
        p.setSurname(surname);
        p.setPatronymic(patronymic);
        //p.setGender(gender);
        p.setBirthdate(birthdate);
        p.setEmail(email);
        p.setPhone(phone);
        p.setPassword(password);
        p.setConfirmPassword(confirmPassword);
        //p.setDistrict();
        //p.setCityId();
        p.setAddress(address);
        //userRepository.save(p);
        try {
            userService.registerNewUserAccount(p);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        final KeeperRatingDbo ratingDbo = ratingDboFactory.createRatingDbo(email, userRepository);
        ratingRepository.save(ratingDbo);
        return "Saved";
    }*/

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
        final FormattingConversionService f = super.mvcConversionService();
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

    // Show Register page.
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String viewRegister(final Model model) {

        final AppUserForm form = new AppUserForm();
        final Iterable<CityDbo> cities = cityRepository.findAll();
        final List<CityDbo> cityDbos = new ArrayList<>();
        cities.forEach(cityDbo -> cityDbos.add(cityDbo));
        model.addAttribute("appUserForm", form);
        model.addAttribute("cities", cityDbos);

        return "registerPage";
    }

    // This method is called to save the registration information.
    // @Validated: To ensure that this Form
    // has been Validated before this method is invoked.
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String saveRegister(final Model model, //
                               final @ModelAttribute("appUserForm") @Valid AppUserForm appUserForm, //
                               final BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            final Iterable<CityDbo> cities = cityRepository.findAll();
            final List<CityDbo> cityDbos = new ArrayList<>();
            cities.forEach(cityDbo -> cityDbos.add(cityDbo));
            model.addAttribute("cities", cityDbos);
            return "registerPage";
        }
        UserDbo newUser;
        try {
            newUser = createAppUser(appUserForm);
        }
        // Other error!!
        catch (Exception e) {
            final Iterable<CityDbo> cities = cityRepository.findAll();
            final List<CityDbo> cityDbos = new ArrayList<>();
            cities.forEach(cityDbo -> cityDbos.add(cityDbo));
            model.addAttribute("cities", cityDbos);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:registerSuccessful";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(final Model model) {

        return "registerSuccessfulPage";
    }

    public UserDbo createAppUser(final AppUserForm form) {
        //final Long userId = userRepository.getMaxId() + 1;
        final String encrytedPassword = EncryptedPasswordUtils.encode(form.getPassword());

       /* final UserDbo user = new UserDbo(userId, form.getUserName(), //
                                   form.getFirstName(), form.getLastName(), false, //
                                   form.getGender(), form.getEmail(), form.getCountryCode(), //
                                   encrytedPassword);*/
        final UserDbo user = new UserDbo(encrytedPassword, //
                                         form.getFirstName(), form.getLastName(), form.getEmail(), form.getGender());
        userRepository.save(user);
        return user;
    }
}
