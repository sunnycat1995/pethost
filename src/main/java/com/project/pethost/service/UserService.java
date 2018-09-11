package com.project.pethost.service;

import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public UserDbo registerNewUserAccount(UserDbo accountDto)
            throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:  + " + accountDto.getEmail());
        }
        return repository.save(accountDto);
    }

    private boolean emailExist(String email) {
        final UserDbo user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
