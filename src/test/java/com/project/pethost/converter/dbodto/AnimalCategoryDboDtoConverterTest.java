package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dto.AnimalCategoryDto;
import org.junit.Assert;
import org.junit.Test;

public class AnimalCategoryDboDtoConverterTest {
    private final AnimalCategoryDboDtoConverter animalCategoryDboDtoConverter;
    private final Integer TEST_ID = 1;
    private final String TEST_CATEGORY = "cat";

    public AnimalCategoryDboDtoConverterTest() {
        animalCategoryDboDtoConverter = new AnimalCategoryDboDtoConverter();
    }

    @Test
    public void convertToDto() {
        final AnimalCategoryDbo dbo = new AnimalCategoryDbo();
        dbo.setId(TEST_ID);
        dbo.setCategory(TEST_CATEGORY);
        final AnimalCategoryDto dto = animalCategoryDboDtoConverter.convertToDto(dbo);
        Assert.assertTrue("No matches ids", dbo.getId().equals(dto.getId()));
        Assert.assertTrue("No matches categories", dbo.getCategory().equals(dto.getCategory()));
    }

    @Test
    public void convertToDbo() {
        final AnimalCategoryDto dto = new AnimalCategoryDto();
        dto.setId(TEST_ID);
        dto.setCategory(TEST_CATEGORY);
        final AnimalCategoryDbo dbo = animalCategoryDboDtoConverter.convertToDbo(dto);
        Assert.assertTrue("No matches ids", dto.getId().equals(dbo.getId()));
        Assert.assertTrue("No matches categories", dto.getCategory().equals(dbo.getCategory()));
    }
}