package com.project.pethost.converter.dbodto;

public interface DboDtoConverter<Dbo, Dto> {

    Dto convertToDto(Dbo dbo);

    Dbo convertToDbo(Dto dto);
}
