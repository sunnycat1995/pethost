package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dto.PetDto;
import com.project.pethost.repository.AnimalCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PetDboDtoConverterTest {

    @InjectMocks
    private PetDboDtoConverter petDboDtoConverter;

    @Mock
    private AnimalCategoryRepository animalCategoryRepository;

    private final String TEST_NAME = "TEST_NAME";
    private final LocalDate TEST_BIRTHDATE = LocalDate.now();
    private final String TEST_DESCRIPTION = "TEST_DESCRIPTION";
    private final String TEST_AVATAR_URL = "TEST_AVATAR_URL";


    @Test
    public void convertToDto() {
        final PetDbo dbo = new PetDbo();
        dbo.setName(TEST_NAME);
        dbo.setBirthdate(TEST_BIRTHDATE);
        dbo.setDescription(TEST_DESCRIPTION);
        dbo.setAvatarUrl(TEST_AVATAR_URL);

        final PetDto dto = petDboDtoConverter.convertToDto(dbo);

        Assert.assertTrue("No equals names", dbo.getName().equals(dto.getName()));
        Assert.assertTrue("No equals birthdate", dbo.getBirthdate().format(
                DateTimeFormatter.ISO_DATE).equals(dto.getBirthdate()));
        Assert.assertTrue("No equals description", dbo.getDescription().equals(dto.getDescription()));
        Assert.assertTrue("No equals avatar url", dbo.getAvatarUrl().equals(dto.getAvatarUrl()));
    }

    @Test
    public void convertToDbo() {
        final PetDto dto = new PetDto();
        dto.setName(TEST_NAME);
        dto.setBirthdate(TEST_BIRTHDATE.format(DateTimeFormatter.ISO_DATE));
        dto.setDescription(TEST_DESCRIPTION);
        dto.setAvatarUrl(TEST_AVATAR_URL);
        dto.setCategory("1");
        when(animalCategoryRepository.findById(anyInt())).thenReturn(Optional.of(new AnimalCategoryDbo(1, "cat")));

        final PetDbo dbo = petDboDtoConverter.convertToDbo(dto);

        Assert.assertTrue("No equals names", dbo.getName().equals(dto.getName()));
        Assert.assertTrue("No equals birthdate", dbo.getBirthdate().format(
                DateTimeFormatter.ISO_DATE).equals(dto.getBirthdate()));
        Assert.assertTrue("No equals description", dbo.getDescription().equals(dto.getDescription()));
        Assert.assertTrue("No equals avatar url", dbo.getAvatarUrl().equals(dto.getAvatarUrl()));
        Assert.assertTrue("No equals category", dbo.getCategory().getId().equals(Integer.parseInt(dto.getCategory())));
    }
}