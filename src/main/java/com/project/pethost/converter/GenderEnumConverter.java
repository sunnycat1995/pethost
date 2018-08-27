package com.project.pethost.converter;

import com.project.pethost.model.Gender;
import org.springframework.core.convert.converter.Converter;

public class GenderEnumConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(final String source) {
        try {
            return Gender.valueOf(source);
        } catch (Exception e) {
            return null;
        }
    }
}
