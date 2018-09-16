package com.project.pethost.service;

import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.dbo.UserDbo;

public interface UserService {
    UserDbo registerNewUserAccount(final UserDbo accountDto)
            throws EmailExistsException;
}