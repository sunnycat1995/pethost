package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dto.PetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        dto.setBirthdate(dbo.getBirthdate());
        dto.setDescription(dbo.getDescription());
        dto.setAvatarUrl(dbo.getAvatarUrl());
        dto.setCreatedDate(dbo.getCreatedDate());
        return dto;
    }

    @Override
    public PetDbo convertToDbo(final PetDto dto) {
        final PetDbo dbo = new PetDbo();
        dbo.setName(dto.getName());
        dbo.setBirthdate(dto.getBirthdate());
        dbo.setOwner(userDboDtoConverter.convertToDbo(dto.getOwner()));
        dbo.setDescription(dto.getDescription());
        dbo.setAvatarUrl(dto.getAvatarUrl());
        dbo.setCreatedDate(dto.getCreatedDate());
        return dbo;
    }
}
