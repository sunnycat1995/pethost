package com.project.pethost.service;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.exception.EmailNotFoundException;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDbo registerNewUserAccount(final UserDbo accountDto)
            throws EmailNotFoundException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailNotFoundException(
                    "There is an account with that email address:  + " + accountDto.getEmail());
        }
        return userRepository.save(accountDto);
    }

    private boolean emailExist(final String email) {
        final UserDbo user = userRepository.findByEmail(email);
        return user != null;
    }
}
