package com.project.pethost.converter;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverterTest {
    private final StringToLocalDateConverter stringToLocalDateConverter;
    private final String date = "2018-10-07";

    public StringToLocalDateConverterTest() {
        stringToLocalDateConverter = new StringToLocalDateConverter();
    }

    @Test
    public void convert() {
        final LocalDate convertedDate = stringToLocalDateConverter.convert(date);
        String actualDate = convertedDate.format(DateTimeFormatter.ISO_DATE);
        Assert.assertTrue("No matches dates", date.equals(actualDate));
    }
}