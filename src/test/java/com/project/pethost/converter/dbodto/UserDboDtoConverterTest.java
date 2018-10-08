package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class UserDboDtoConverterTest {
    private final UserDboDtoConverter dboDtoConverter;
    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "TEST_NAME";
    private final String TEST_SURNAME = "TEST_SURNAME";
    private final String TEST_PATRONYMIC = "TEST_PATRONYMIC";
    private final String TEST_EMAIL = "test@email.com";
    private final boolean TEST_ENABLED = true;
    private final LocalDateTime TEST_CREATED_DATE = LocalDateTime.now();


    public UserDboDtoConverterTest() {
        dboDtoConverter = new UserDboDtoConverter();
    }

    @Test
    public void convertToDto() {
        final UserDbo dbo = new UserDbo();
        dbo.setId(TEST_ID);
        dbo.setName(TEST_NAME);
        dbo.setSurname(TEST_SURNAME);
        dbo.setPatronymic(TEST_PATRONYMIC);
        dbo.setEmail(TEST_EMAIL);
        dbo.setEnabled(TEST_ENABLED);
        dbo.setCreatedDate(TEST_CREATED_DATE);

        final UserDto dto = dboDtoConverter.convertToDto(dbo);

        Assert.assertTrue("No matches ids", dbo.getId().equals(dto.getId()));
        Assert.assertTrue("No matches names", dbo.getName().equals(dto.getName()));
        Assert.assertTrue("No matches surnames", dbo.getSurname().equals(dto.getSurname()));
        Assert.assertTrue("No matches patronymics", dbo.getPatronymic().equals(dto.getPatronymic()));
        Assert.assertTrue("No matches emails", dbo.getEmail().equals(dto.getEmail()));
        Assert.assertTrue("No matches enable identificator", dbo.getEnabled().equals(dto.getEnabled()));
        Assert.assertTrue("No matches created date", dbo.getCreatedDate().equals(dto.getCreatedDate()));
    }

    @Test
    public void convertToDbo() {
        final UserDto dto = new UserDto();
        dto.setId(TEST_ID);
        dto.setName(TEST_NAME);
        dto.setSurname(TEST_SURNAME);
        dto.setPatronymic(TEST_PATRONYMIC);
        dto.setEmail(TEST_EMAIL);
        dto.setEnabled(TEST_ENABLED);
        dto.setCreatedDate(TEST_CREATED_DATE);

        final UserDbo dbo = dboDtoConverter.convertToDbo(dto);

        Assert.assertTrue("No matches ids", dto.getId().equals(dbo.getId()));
        Assert.assertTrue("No matches names", dto.getName().equals(dbo.getName()));
        Assert.assertTrue("No matches surnames", dto.getSurname().equals(dbo.getSurname()));
        Assert.assertTrue("No matches patronymics", dto.getPatronymic().equals(dbo.getPatronymic()));
        Assert.assertTrue("No matches emails", dto.getEmail().equals(dbo.getEmail()));
        Assert.assertTrue("No matches enable identificator", dto.getEnabled().equals(dbo.getEnabled()));
        Assert.assertTrue("No matches created date", dto.getCreatedDate().equals(dbo.getCreatedDate()));
    }
}