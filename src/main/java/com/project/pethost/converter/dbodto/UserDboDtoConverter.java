package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserDboDtoConverter implements DboDtoConverter<UserDbo, UserDto> {

    @Override
    public UserDto convertToDto(final UserDbo userDbo) {
        final UserDto userDto = new UserDto();
        userDto.setId(userDbo.getId());
        userDto.setName(userDbo.getName());
        userDto.setSurname(userDbo.getSurname());
        userDto.setPatronymic(userDbo.getPatronymic());
        userDto.setEmail(userDbo.getEmail());
        userDto.setEnabled(userDbo.getEnabled());
        userDto.setCreatedDate(userDbo.getCreatedDate());
        return userDto;
    }

    @Override
    public UserDbo convertToDbo(final UserDto userDto) {
        final UserDbo userDbo = new UserDbo();
        userDbo.setId(userDto.getId());
        userDbo.setName(userDto.getName());
        userDbo.setSurname(userDto.getSurname());
        userDbo.setPatronymic(userDto.getPatronymic());
        userDbo.setEmail(userDto.getEmail());
        userDbo.setEnabled(userDto.getEnabled());
        userDbo.setCreatedDate(userDto.getCreatedDate());
        return userDbo;
    }
}
