package com.project.pethost.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

@SpringView(name = ErrorView.NAME) public class ErrorView extends CustomComponent implements View {
	public static final String NAME = "error";

	@Override public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		final Label label = new Label("Where do we go, Susanin?");
		final String imageURL = "/opt/projects/pethost/susanin.jpg";
		final FileResource resource = new FileResource(new File(imageURL));
		final Embedded embedded = new Embedded(null, resource);
		setCompositionRoot(new VerticalLayout(label, embedded));
	}
}
