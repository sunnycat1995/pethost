package com.project.pethost.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;

@SpringView(name = MenuView.NAME) public class MenuView extends CustomComponent implements View {
	public static final String NAME = "menu";

	@Override public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		final MenuBar menuBar = new MenuBar();
		menuBar.addItem("Email", (MenuBar.Command) e -> {
			getUI().getNavigator().navigateTo(EmailForm.NAME);
		});
		menuBar.addItem("Error", (MenuBar.Command) e -> {
			getUI().getNavigator().navigateTo(ErrorView.NAME);
		});
		this.setCompositionRoot(menuBar);
	}
}
