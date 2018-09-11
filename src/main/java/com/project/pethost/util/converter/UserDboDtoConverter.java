package com.project.pethost.util.converter;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserDboDtoConverter implements DboDtoConverter<UserDbo, UserDto> {

    @Override
    public UserDto convertToDto(final UserDbo userDbo) {
        final UserDto userDto = new UserDto();
        return userDto;
    }

    @Override
    public UserDbo convertToDbo(final UserDto userDto) {
        final UserDbo userDbo = new UserDbo();
        return userDbo;
    }
}
