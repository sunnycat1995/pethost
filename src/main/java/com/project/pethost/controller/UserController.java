package com.project.pethost.controller;

import com.project.pethost.converter.GenderEnumConverter;
import com.project.pethost.converter.UserDboDtoConverter;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.factory.RatingDboFactory;
import com.project.pethost.form.AppUserForm;
import com.project.pethost.repository.AnimalCategoryRepository;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final AnimalCategoryRepository animalCategoryRepository;


    @Autowired
    public UserController(final UserRepository userRepository,
                          final UserServiceImpl userService,
                          final UserDboDtoConverter userDboDtoConverter,
                          final KeeperRatingRepository ratingRepository,
                          final RatingDboFactory ratingDboFactory,
                          final AppUserValidator appUserValidator,
                          final CityRepository cityRepository,
                          final AnimalCategoryRepository animalCategoryRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDboDtoConverter = userDboDtoConverter;
        this.ratingRepository = ratingRepository;
        this.ratingDboFactory = ratingDboFactory;
        this.appUserValidator = appUserValidator;
        this.cityRepository = cityRepository;
        this.animalCategoryRepository = animalCategoryRepository;
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(final WebDataBinder dataBinder) {
        // Form target
        final Object target = dataBinder.getTarget();
        LOGGER.info("Target=" + target);
        if (target != null) {
            if (target.getClass() == AppUserForm.class) {
                dataBinder.setValidator(appUserValidator);
            }
        }
    }

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

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String viewRegister(final Model model) {

        final AppUserForm form = new AppUserForm();
        final List<CityDbo> cityDbos = cities();
        final List<AnimalCategoryDbo> animalCategoryDbos = animalCategories();
        model.addAttribute("appUserForm", form);
        model.addAttribute("cities", cityDbos);
        model.addAttribute("animalPreferences", animalCategoryDbos);

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
        final List<AnimalCategoryDbo> animalCategoryDbos = animalCategories();

        // Validate result
        if (result.hasErrors()) {
            final List<CityDbo> cityDbos = cities();
            model.addAttribute("cities", cityDbos);
            model.addAttribute("animalPreferences", animalCategoryDbos);
            return "registerPage";
        }
        UserDbo newUser;
        try {
            newUser = createAppUser(appUserForm);
        }
        // Other error!!
        catch (final Exception e) {
            final List<CityDbo> cityDbos = cities();
            model.addAttribute("cities", cityDbos);
            model.addAttribute("animalPreferences", animalCategoryDbos);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage";
        }

        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:registerSuccessful";
    }

    private List<CityDbo> cities() {
        final Iterable<CityDbo> cities = cityRepository.findAll();
        final List<CityDbo> cityDbos = new ArrayList<>();
        cities.forEach(cityDbos::add);
        cityDbos.sort(Comparator.comparing(CityDbo::getName));
        return cityDbos;
    }

    private List<AnimalCategoryDbo> animalCategories() {
        final Iterable<AnimalCategoryDbo> animalCategories = animalCategoryRepository.findAll();
        final List<AnimalCategoryDbo> animalCategoryDbos = new ArrayList<>();
        animalCategories.forEach(animalCategoryDbos::add);
        animalCategoryDbos.sort(Comparator.comparing(AnimalCategoryDbo::getCategory));
        return animalCategoryDbos;
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(final Model model) {

        return "registerSuccessfulPage";
    }

    public UserDbo createAppUser(final AppUserForm form) {
        final String encrytedPassword = EncryptedPasswordUtils.encode(form.getPassword());

        final Iterable<AnimalCategoryDbo> animalCategories = animalCategoryRepository.findAll();
        final List<AnimalCategoryDbo> animalCategoryDbos = new ArrayList<>();
        animalCategories.forEach(animalCategoryDbos::add);

        final Set<AnimalCategoryDbo> animalCategoryPreference = new HashSet<>();

        final String[] userAnimalPreferences = form.getAnimalPreferences();
        Arrays.stream(userAnimalPreferences).forEach(preference -> {
            animalCategoryDbos.forEach(category -> {
                if (preference.equals(category.getCategory())) {
                    animalCategoryPreference.add(category);
                }
            });
        });
        final UserDbo user = new UserDbo(encrytedPassword,
                                         form.getFirstName(), form.getLastName(), form.getEmail(), form.getGender(),
                                         animalCategoryPreference);
        userRepository.save(user);
        return user;
    }
}
