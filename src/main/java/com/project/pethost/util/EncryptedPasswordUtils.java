package com.project.pethost.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptedPasswordUtils {

    // Encryte Password with BCryptPasswordEncoder
    public static String encode(final String password) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        final String password = "123";
        final String encrytedPassword = encode(password);

        System.out.println("Encryted Password: " + encrytedPassword);
    }

}
