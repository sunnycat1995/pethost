package com.project.pethost.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringUI(path = "vaadin4") @Theme("mytheme") public class StringListField extends CustomField<List<String>> {
	private VerticalLayout layout = new VerticalLayout();

    /*public StringListField(final String caption) {
        setCaption(caption);
    }*/

	@Override protected Component initContent() {
		final GridLayout gridLayout = new GridLayout(1, 2);
		gridLayout.addComponent(layout);

		List<String> list = getValue();
		if (list == null) {
			list = new ArrayList<>();
		}
		redraw(list);

		final Button button = new Button("Add recipient", this::addItem);
		gridLayout.addComponent(button);

		return gridLayout;
	}

	public void addItem(final Button.ClickEvent event) {
		List<String> list = getValue();
		if (list == null) {
			list = new ArrayList<>();
		}
		final List<String> copyList = new ArrayList<>(list);
		copyList.add("");
		setValue(copyList);
		redraw(list);
	}

	@SuppressWarnings("unchecked") @Override public Class<? extends List<String>> getType() {
		return (Class<List<String>>) Arrays.stream(ArrayList.class.getInterfaces())
				.filter(a -> a.isAssignableFrom(List.class))
				.findAny()
				.orElseThrow(RuntimeException::new);
	}

	@Override public List<String> getValue() {
		return super.getValue();
	}

	public void redraw(final List<String> list) throws ReadOnlyException, Converter.ConversionException {
		layout.removeAllComponents();
		final List<String> copyList = new ArrayList<>(list);
		for (int i = 0; i < copyList.size(); ++i) {
			final TextField textField = new TextField();
			textField.setValue(copyList.get(i));

			final int finalI = i;
			textField.addValueChangeListener(valueChange -> {
				copyList.set(finalI, textField.getValue());
				setValue(copyList);
			});

			final Button removeButton = new Button("Remove");
			final HorizontalLayout horizontalLayout = new HorizontalLayout(textField, removeButton);
			removeButton.addClickListener(e -> {
				final TextField participant = (TextField) horizontalLayout.getComponent(0);
				copyList.remove(participant.getValue());
				layout.removeComponent(horizontalLayout);
				setValue(copyList);
			});
			removeButton.addClickListener(e -> layout.removeComponent(horizontalLayout));
			layout.addComponent(horizontalLayout);
		}
	}
}
