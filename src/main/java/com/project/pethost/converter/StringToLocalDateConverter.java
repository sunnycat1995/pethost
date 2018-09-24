package com.project.pethost.converter;

import com.project.pethost.constant.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(final String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
    }
}
