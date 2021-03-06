package com.project.pethost.vaadin;

import com.project.pethost.vaadin.converter.StringToListConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringView(name = EmailForm.NAME) @Theme("mytheme") public class EmailView extends CustomComponent implements View {

	@Override public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		{
			final ArrayList<String> recipients1 = new ArrayList<>();
			recipients1.add("Maksim");
			recipients1.add("Demian");

			final ArrayList<String> recipients2 = new ArrayList<>();
			recipients2.add("Vova");
			recipients2.add("Alexander");

			final ArrayList<String> recipients3 = new ArrayList<>();
			recipients3.add("Roma");

			final Email email1 = new Email("Irina", "Vaadin quesions", recipients1, LocalDate.now().minusDays(3));
			final Email email2 = new Email("Maksim", "Spring quesions", recipients2, LocalDate.now().minusDays(2));
			final Email email3 = new Email("Pasha", "JS quesions", recipients3, LocalDate.now().minusDays(1));

			final List<Email>[] emails = new List[1];
			emails[0] = Arrays.asList(email1, email2, email3);
			final BeanItemContainer container = new BeanItemContainer(Email.class, emails[0]);

			final Grid grid = new Grid();
			grid.setContainerDataSource(container);
			grid.setColumnOrder("date", "name", "message", "recipients");
			final Grid.Column recipients = grid.getColumn("recipients");
			recipients.setConverter(new StringToListConverter());

			grid.setSizeFull();
			grid.setSelectionMode(Grid.SelectionMode.MULTI);

			final Button addButton = new Button("Add", e -> {
				final Window window = new Window("New email");
				final Email email = new Email("", "", new ArrayList<>(), LocalDate.now());
				container.addItem(email); //TODO fix cancel button
				addWindow(grid, window, email);
			});

			final Button editButton = new Button("Edit");
			editButton.setEnabled(false);
			editButton.addClickListener(e -> {
				final Email email = (Email) grid.getSelectedRows().iterator().next();
				final Window window = new Window("EmailForm");
				addWindow(grid, window, email);
			});

			final Button deleteButton = new Button("Remove", e -> {
				final Collection<Object> selectedRows = grid.getSelectedRows();

				selectedRows.forEach(selectedRow -> {
					container.removeItem(selectedRow);
				});
				grid.refreshAllRows();

			});
			deleteButton.setEnabled(false);
			grid.addSelectionListener(e -> {
				if (!e.getSelected().isEmpty()) {
					deleteButton.setEnabled(true);
					if (e.getSelected().size() == 1) {
						editButton.setEnabled(true);
					} else {
						editButton.setEnabled(false);
					}
				} else {
					deleteButton.setEnabled(false);
					editButton.setEnabled(false);
				}
			});

			final HorizontalLayout buttonsLayout = new HorizontalLayout(addButton, editButton, deleteButton);
			final VerticalLayout layout = new VerticalLayout(grid, buttonsLayout);

			final Button closeButton = new Button("Close", e -> {
				getUI().getNavigator().navigateTo(MenuView.NAME);
			});
			layout.addComponent(closeButton);
			this.setCompositionRoot(layout);
		}
	}

	private void addWindow(final Grid grid, final Window window, final Email email) {
		final EmailForm emailForm = new EmailForm(email, window::close);
		window.setContent(emailForm);
		window.setClosable(false);
		window.addCloseListener(listener -> {
			grid.refreshAllRows();
		});
		window.setSizeFull();
		this.getUI().addWindow(window);
	}
}
