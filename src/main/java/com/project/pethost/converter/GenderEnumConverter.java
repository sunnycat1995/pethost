package com.project.pethost.converter;

import com.project.pethost.dbo.GenderDbo;
import org.springframework.core.convert.converter.Converter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GenderEnumConverter implements Converter<String, GenderDbo> {
    private Logger LOGGER = Logger.getLogger(GenderEnumConverter.class.getName());

    @Override
    public GenderDbo convert(final String source) {
        try {
            return GenderDbo.valueOf(source);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "Incorrect gender value " + source);
            return GenderDbo.NO_CHOSEN;
        }
    }
}
