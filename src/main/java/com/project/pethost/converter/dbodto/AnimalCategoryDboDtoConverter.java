package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dto.AnimalCategoryDto;
import org.springframework.stereotype.Service;

@Service
public class AnimalCategoryDboDtoConverter implements DboDtoConverter<AnimalCategoryDbo, AnimalCategoryDto> {
    @Override
    public AnimalCategoryDto convertToDto(final AnimalCategoryDbo dbo) {
        final AnimalCategoryDto dto = new AnimalCategoryDto();
        dto.setId(dbo.getId());
        dto.setCategory(dbo.getCategory());
        return dto;
    }

    @Override
    public AnimalCategoryDbo convertToDbo(final AnimalCategoryDto dto) {
        final AnimalCategoryDbo dbo = new AnimalCategoryDbo();
        dbo.setId(dto.getId());
        dbo.setCategory(dto.getCategory());
        return dbo;
    }
}
