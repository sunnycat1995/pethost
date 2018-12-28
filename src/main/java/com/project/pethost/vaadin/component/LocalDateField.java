package com.project.pethost.vaadin.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;

import java.time.LocalDate;

public class LocalDateField extends CustomField<LocalDate> {
    @Override
    protected Component initContent() {
        return new DateField();
    }

    @Override
    public Class<? extends LocalDate> getType() {
        return LocalDate.class;
    }
}
