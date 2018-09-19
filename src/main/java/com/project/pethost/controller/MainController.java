package com.project.pethost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/pethost")
public class MainController extends WebMvcConfigurationSupport {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @RequestMapping(value = {"", "/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(final Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "Welcome to the Pethost page!");
        model.addAttribute("content", "Content text");
        return "welcomePage";
    }

    @RequestMapping(value = "/createReview", method = RequestMethod.GET)
    public @ResponseBody String createReview() { //(only by ownerId about keeperId)
        return "Created review";
    }

    @RequestMapping(value = "/recalculateRating", method = RequestMethod.GET)
    public @ResponseBody String recalculateRating() { //(only by ownerId about keeperId)
        return "Recalculated rating";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody String error() { //(only by ownerId about keeperId)
        return "Error";
    }
}

