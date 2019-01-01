package com.project.pethost.vaadin;

import com.project.pethost.vaadin.component.LocalDateField;
import com.vaadin.annotations.Theme;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;

@Theme("myformtheme")
public class EmailForm extends CustomComponent {
	public final static String NAME = "email";

	@PropertyId("name")
	private final TextField nameTextField = new TextField("Name");
	@PropertyId("message")
	private final TextArea messageTextField = new TextArea("Message");
	@PropertyId("recipients")
	private StringListField recipientsField = new StringListField();
	@PropertyId("date")
	private LocalDateField dateField = new LocalDateField();

	private final FieldGroup emailFieldGroup = new FieldGroup();
	private Runnable notifyOk;

	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");

	public EmailForm(final Email email, final Runnable onSaveOrDiscard) {
		this.notifyOk = onSaveOrDiscard;
		save.addClickListener(e -> {
			commit(e);
			onSaveOrDiscard.run();
		});
		cancel.addClickListener(e -> {
			discard(e);
			onSaveOrDiscard.run();
		});
		fillEmailForm(email);
	}

	private void fillEmailForm(final Email email) {
		recipientsField.setCaption("Recipients");
		dateField.setCaption("Date");
		dateField.addValueChangeListener(e -> {
			final LocalDate localDate = (LocalDate) e.getProperty().getValue();
			email.setDate(localDate);
		});
		final HorizontalLayout buttonsLayout = new HorizontalLayout(save, cancel);
		final VerticalLayout verticalLayout =
				new VerticalLayout(nameTextField, messageTextField, recipientsField, dateField, buttonsLayout);
		emailFieldGroup.setItemDataSource(new BeanItem<Email>(email));
		emailFieldGroup.bindMemberFields(this);
		addStyleName("email-form");
		setCompositionRoot(verticalLayout);
	}

	public void commit(final Button.ClickEvent event) {
		try {
			emailFieldGroup.commit();
			notifyOk.run();
		} catch (final FieldGroup.CommitException e) {
			Notification.show("Commit failed");
		}
	}

	public void discard(final Button.ClickEvent event) {
		try {
			emailFieldGroup.discard();
			notifyOk.run();
		} catch (final Exception e) {
			Notification.show("Discard failed");
		}
	}
}
