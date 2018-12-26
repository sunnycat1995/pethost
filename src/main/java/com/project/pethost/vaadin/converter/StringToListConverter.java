package com.project.pethost.vaadin.converter;

import com.vaadin.data.util.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringToListConverter implements Converter<String, ArrayList<String>> {
    @Override
    public ArrayList<String> convertToModel(final String s, final Class<? extends ArrayList<String>> aClass, final Locale locale)
        throws ConversionException {
        final String[] token = s.split(",");
        Arrays.stream(token).forEach(String::trim);
        final ArrayList<String> strings = new ArrayList<>(Arrays.asList(token));
        return strings;
    }

    @Override
    public String convertToPresentation(final ArrayList<String> strings,
                                        final Class<? extends String> aClass,
                                        final Locale locale)
        throws ConversionException {
        return strings.stream().collect(Collectors.joining(", "));
    }

    @Override
    public Class<ArrayList<String>> getModelType() {
        final List<String> strings = new ArrayList<>();
        return (Class<ArrayList<String>>) strings.getClass();
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}