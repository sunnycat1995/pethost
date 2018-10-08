package com.project.pethost.converter;

import com.project.pethost.dbo.GenderDbo;
import org.junit.Assert;
import org.junit.Test;

public class GenderEnumConverterTest {
    private final GenderEnumConverter genderEnumConverter;

    public GenderEnumConverterTest() {
        genderEnumConverter = new GenderEnumConverter();
    }

    @Test
    public void convertMale() {
        final GenderDbo gender = genderEnumConverter.convert("MALE");
        Assert.assertTrue("Not matches gender", gender.equals(GenderDbo.MALE));
    }

    @Test
    public void convertFemale() {
        final GenderDbo gender = genderEnumConverter.convert("female");
        Assert.assertTrue("Not matches gender", gender.equals(GenderDbo.FEMALE));
    }

    @Test
    public void convertNoCorrectGender() {
        final GenderDbo gender = genderEnumConverter.convert("no correct");
        Assert.assertTrue("Not matches gender", gender.equals(GenderDbo.NO_CHOSEN));
    }
}