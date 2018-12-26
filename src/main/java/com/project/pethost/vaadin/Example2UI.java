package com.project.pethost.vaadin;

import com.project.pethost.vaadin.converter.StringToListConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.*;
import java.util.stream.Collectors;

@SpringUI(path = "vaadin2")
@Theme("valo")
public class Example2UI extends UI {
    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final ArrayList<String> recipients1 = new ArrayList<>();
        recipients1.add("Maksim");
        recipients1.add("Demian");

        final ArrayList<String> recipients2 = new ArrayList<>();
        recipients2.add("Vova");
        recipients2.add("Alexander");

        final ArrayList<String> recipients3 = new ArrayList<>();
        recipients3.add("Roma");

        final Email email1 = new Email("Irina", "Vaadin quesions", recipients1);
        final Email email2 = new Email("Maksim", "Spring quesions", recipients2);
        final Email email3 = new Email("Pasha", "JS quesions", recipients3);

        final List<Email>[] emails = new List[1];
        emails[0] = Arrays.asList(email1, email2, email3);
        final BeanItemContainer[] container = {new BeanItemContainer(Email.class, emails[0])};

        final Grid grid = new Grid();
        grid.setContainerDataSource(container[0]);
        grid.setColumnOrder("name", "message", "recipients");
        final Grid.Column recipients = grid.getColumn("recipients");
        recipients.setConverter(new StringToListConverter());

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        final Button deleteButton = new Button("Remove", e -> {
            final Collection<Object> selectedRows = grid.getSelectedRows();
            if (!selectedRows.isEmpty()) {
                selectedRows.forEach(selectedRow -> {
                    final Optional<Email> first = emails[0].stream().filter(el -> el.equals(selectedRow)).findFirst();
                    first.ifPresent(email -> emails[0] = emails[0].stream().filter(el -> !el.equals(selectedRow)).collect(
                        Collectors.toList()));
                });
                final BeanItemContainer container1 = new BeanItemContainer(Email.class, emails[0]);
                grid.setContainerDataSource(container1);
                grid.refreshAllRows();
            }
        });
        deleteButton.setEnabled(false);
        grid.addSelectionListener(e -> {
            if(!e.getSelected().isEmpty()) {
                deleteButton.setEnabled(true);
            }
            else {
                deleteButton.setEnabled(false);
            }
        });

        final VerticalLayout layout = new VerticalLayout(grid, deleteButton);

        final StringListField stringListField = new StringListField();
        layout.addComponent(stringListField);
        setContent(layout);
    }
}
