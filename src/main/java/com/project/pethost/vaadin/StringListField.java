package com.project.pethost.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringUI(path = "vaadin4")
@Theme("valo")
public class StringListField extends CustomField<List<String>> {
    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected Component initContent() {
        final GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.addComponent(layout);

        final Button button = new Button("Add recipient", this::addItem);
        gridLayout.addComponent(button);

        return gridLayout;
    }

    public void addItem(final Button.ClickEvent event) {
        List<String> list = getValue();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add("");
        setValue(list);

        final TextField textField = new TextField();
        textField.addValueChangeListener(valueChange -> {
            final int index = layout.getComponentIndex(textField);
            final List<String> values = getValue();
            values.set(index, textField.getValue());
            setValue(values);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends List<String>> getType() {
        return (Class<List<String>>) Arrays.stream(ArrayList.class.getInterfaces())
            .filter(a -> a.isAssignableFrom(List.class)).findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<String> getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(final List<String> newFieldValue) throws ReadOnlyException, Converter.ConversionException {
        final List<String> copyList = new ArrayList<>(newFieldValue);
        copyList.forEach(value -> {
            final TextField textField = new TextField(value);
            final Button button = new Button("Remove");
            final HorizontalLayout horizontalLayout = new HorizontalLayout(textField, button);
            button.addClickListener(e -> layout.removeComponent(horizontalLayout));
            layout.addComponent(horizontalLayout);
        });
    }
}
