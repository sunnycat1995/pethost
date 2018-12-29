package com.project.pethost.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;

@SpringView(name = EmailView.NAME)
public class EmailView extends CustomComponent implements View {
	public final static String NAME = "email";

	@Override public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {

	}
}
