package com.project.pethost.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SpringUI(path = "vaadin5")
@Theme("mytheme")
public class EmailUI extends UI {

    private final SpringViewProvider springViewProvider;

    public EmailUI(final SpringViewProvider springViewProvider) {
        this.springViewProvider = springViewProvider;
    }

    @Override protected void init(final VaadinRequest vaadinRequest) {
        final Navigator navigator = new Navigator(this, this);
        navigator.addProvider(springViewProvider);
        navigator.navigateTo(MenuView.NAME);
        navigator.setErrorView(new ErrorView());
    }
}
