package com.project.pethost.service;

import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.model.Person;

public interface IUserService {
    Person registerNewUserAccount(final Person accountDto)
            throws EmailExistsException;
}