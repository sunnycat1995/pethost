package com.project.pethost.vaadin.component;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateField extends CustomField<LocalDate> {
    @Override
    protected Component initContent() {
        return new DateField("", Date.from(getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public Class<? extends LocalDate> getType() {
        return LocalDate.class;
    }

	@Override public LocalDate getValue() {
		return super.getValue();
	}

	@Override public void setValue(final LocalDate newFieldValue)
			throws ReadOnlyException, Converter.ConversionException {
		super.setValue(newFieldValue);
	}
}
