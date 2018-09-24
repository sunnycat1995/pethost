package com.project.pethost.converter.dbodto;

import com.project.pethost.constant.Constants;
import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dto.PetDto;
import com.project.pethost.repository.AnimalCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PetDboDtoConverter implements DboDtoConverter<PetDbo, PetDto> {

    private AnimalCategoryRepository animalCategoryRepository;

    @Autowired
    public PetDboDtoConverter(final AnimalCategoryRepository animalCategoryRepository) {
        this.animalCategoryRepository = animalCategoryRepository;
    }

    @Override
    public PetDto convertToDto(final PetDbo dbo) {
        final PetDto dto = new PetDto();
        dto.setName(dbo.getName());
        dto.setBirthdate(dbo.getBirthdate().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
        dto.setDescription(dbo.getDescription());
        dto.setAvatarUrl(dbo.getAvatarUrl());
        return dto;
    }

    @Override
    public PetDbo convertToDbo(final PetDto dto) {
        final PetDbo dbo = new PetDbo();
        dbo.setName(dto.getName());
        dbo.setBirthdate(LocalDate.parse(dto.getBirthdate(), DateTimeFormatter.ISO_DATE));
        final Integer categoryId = Integer.valueOf(dto.getCategory());

        final Optional<AnimalCategoryDbo> animalCategoryDbo = animalCategoryRepository.findById(categoryId);
        animalCategoryDbo.ifPresent(dbo::setCategory);
        dbo.setDescription(dto.getDescription());
        dbo.setAvatarUrl(dto.getAvatarUrl());
        return dbo;
    }
}
