package com.project.pethost.converter;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dto.UserDto;
import com.project.pethost.util.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDboDtoConverter implements DboDtoConverter<UserDbo, UserDto> {

    @Autowired
    private EncryptedPasswordUtils encryptedPasswordUtils;

    public UserDboDtoConverter(final EncryptedPasswordUtils encryptedPasswordUtils) {
        this.encryptedPasswordUtils = encryptedPasswordUtils;
    }

    @Override
    public UserDto convertToDto(final UserDbo userDbo) {
        final UserDto userDto = new UserDto();
        return userDto;
    }

    @Override
    public UserDbo convertToDbo(final UserDto userDto) {
        final UserDbo userDbo = new UserDbo();
        userDbo.setId(userDto.getId());
        userDbo.setName(userDto.getName());
        userDbo.setEmail(userDto.getEmail());
        //userDbo.setPassword(encryptedPasswordUtils.encode(userDto.getPassword()));
        return userDbo;
    }
}
