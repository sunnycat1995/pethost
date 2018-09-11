package com.project.pethost.util.converter;

public interface DboDtoConverter<Dbo, Dto> {

    Dto convertToDto(Dbo dbo);

    Dbo convertToDbo(Dto dto);
}
