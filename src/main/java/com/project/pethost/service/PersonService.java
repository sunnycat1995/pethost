package com.project.pethost.service;

import com.project.pethost.exception.EmailExistsException;
import com.project.pethost.model.Person;
import com.project.pethost.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements IUserService {
    @Autowired
    private PersonRepository repository;

    @Transactional
    @Override
    public Person registerNewUserAccount(Person accountDto)
            throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:  + " + accountDto.getEmail());
        }
        return repository.save(accountDto);
    }

    private boolean emailExist(String email) {
        final Person user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
