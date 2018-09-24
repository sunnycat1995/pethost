package com.project.pethost.controller;

import com.project.pethost.converter.GenderEnumConverter;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.RoleDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.UserRoleDbo;
import com.project.pethost.dbo.UserRoleTypeDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.exception.CityOutOfBoundException;
import com.project.pethost.form.UserCreationForm;
import com.project.pethost.repository.RoleRepository;
import com.project.pethost.repository.UserRepository;
import com.project.pethost.repository.UserRoleRepository;
import com.project.pethost.service.DataService;
import com.project.pethost.util.EncryptedPasswordUtils;
import com.project.pethost.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class UserController extends WebMvcConfigurationSupport {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final AppUserValidator appUserValidator;
    private final DataService dataService;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserController(final UserRepository userRepository,
                          final AppUserValidator appUserValidator,
                          final DataService dataService,
                          final UserRoleRepository userRoleRepository,
                          final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.appUserValidator = appUserValidator;
        this.dataService = dataService;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(final WebDataBinder dataBinder) {
        // Form target
        final Object target = dataBinder.getTarget();
        LOGGER.info("Target=" + target);
        if (target != null) {
            if (target.getClass() == UserCreationForm.class) {
                dataBinder.setValidator(appUserValidator);
            }
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(final Model model, final Principal principal) {
        if (principal != null) {
            return performUserInfoPage(model, principal);
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
            return performUserInfoPage(model, principal);
        }
        return "loginPage";
    }

    private String performUserInfoPage(final Model model, final Principal principal) {
        final String userName = principal.getName();
        LOGGER.info("User Email: " + userName);
        final UserDbo userDbo = userRepository.findByEmail(userName);
        model.addAttribute("currentUser", userDbo);
        return "userInfoPage";
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
    public String getAllUsers(final Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/allUsersPage";
    }

    @RequestMapping(value = "/searchUsersByAnimalPreferences", method = RequestMethod.GET)
    public @ResponseBody String searchUsersByAnimalPreferences() {
        return "Returned all users filtered by animal categories preferences";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String viewRegister(final Model model) {

        final UserCreationForm form = new UserCreationForm();
        final List<CityDbo> cityDbos = dataService.cities();
        final List<AnimalCategoryDbo> animalCategoryDbos = dataService.animalCategories();
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
                               final @ModelAttribute("appUserForm") @Valid UserCreationForm appUserForm, //
                               final BindingResult result, //
                               final RedirectAttributes redirectAttributes) {
        final List<AnimalCategoryDbo> animalCategoryDbos = dataService.animalCategories();

        // Validate result
        if (result.hasErrors()) {
            final List<CityDbo> cityDbos = dataService.cities();
            model.addAttribute("cities", cityDbos);
            model.addAttribute("animalPreferences", animalCategoryDbos);
            return "registerPage";
        }
        UserDbo newUser;
        try {
            newUser = createAppUser(appUserForm);
            createUserRole(newUser);
            redirectAttributes.addFlashAttribute("flashUser", newUser);
        }
        // Other error!!
        catch (final CityOutOfBoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            final List<CityDbo> cityDbos = dataService.cities();
            model.addAttribute("cities", cityDbos);
            model.addAttribute("animalPreferences", animalCategoryDbos);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerPage";
        }

        return "redirect:registerSuccessful";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(final Model model) {

        return "registerSuccessfulPage";
    }

    private UserDbo createAppUser(final UserCreationForm form) throws CityOutOfBoundException {
        final String encryptedPassword = EncryptedPasswordUtils.encode(form.getPassword());

        final List<AnimalCategoryDbo> animalCategoryDbos = dataService.animalCategories();
        final Set<AnimalCategoryDbo> animalCategoryPreference = new HashSet<>();

        final String[] userAnimalPreferences = form.getAnimalPreferences();
        Arrays.stream(userAnimalPreferences).forEach(preference -> {
            animalCategoryDbos.forEach(category -> {
                if (preference.equals(category.getCategory())) {
                    animalCategoryPreference.add(category);
                }
            });
        });

        final String cityName = form.getCountryCode();
        final List<CityDbo> cities = dataService.findAllCitiesByName(cityName);

        if (cities.size() == 0) {
            throw new CityOutOfBoundException("Not found city " + cityName + " in results");
        }
        if (cities.size() > 1) {
            throw new CityOutOfBoundException("Returned more than 1 city with the same name " + cityName);
        }

        final CityDbo cityDbo = cities.get(0);

        final UserDbo user = new UserDbo(encryptedPassword,
                                         form.getFirstName(), form.getLastName(), form.getEmail(), form.getGender(),
                                         cityDbo,
                                         animalCategoryPreference);
        userRepository.save(user);

        return user;
    }

    private void createUserRole(final UserDbo user) {
        final UserRoleDbo userRole = new UserRoleDbo();
        userRole.setUserId(userRepository.findByEmail(user.getEmail()).getId());
        final UserRoleTypeDbo roleUser = UserRoleTypeDbo.ROLE_USER;
        final RoleDbo roleDbo = roleRepository.findByRole(roleUser);
        userRole.setRoleId(roleDbo.getId());
        userRoleRepository.save(userRole);
    }
}
