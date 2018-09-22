package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dto.PetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PetDboDtoConverter implements DboDtoConverter<PetDbo, PetDto> {

    private UserDboDtoConverter userDboDtoConverter;

    @Autowired
    public PetDboDtoConverter(final UserDboDtoConverter userDboDtoConverter) {
        this.userDboDtoConverter = userDboDtoConverter;
    }

    @Override
    public PetDto convertToDto(final PetDbo dbo) {
        final PetDto dto = new PetDto();
        dto.setName(dbo.getName());
        dto.setBirthdate(dbo.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setDescription(dbo.getDescription());
        dto.setAvatarUrl(dbo.getAvatarUrl());
        return dto;
    }

    @Override
    public PetDbo convertToDbo(final PetDto dto) {
        final PetDbo dbo = new PetDbo();
        dbo.setName(dto.getName());
        dbo.setBirthdate(LocalDate.parse(dto.getBirthdate(), DateTimeFormatter.ISO_DATE));
        dbo.setDescription(dto.getDescription());
        dbo.setAvatarUrl(dto.getAvatarUrl());
        return dbo;
    }
}
