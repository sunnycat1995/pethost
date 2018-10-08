package com.project.pethost.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtilsTest {
    private final String PASSWORD = "SomeCoolPassword";

    @Test
    public void checkCorrectPassword() {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encodedPassword = bCryptPasswordEncoder.encode(PASSWORD);
        Assert.assertTrue("No correct password", bCryptPasswordEncoder.matches(PASSWORD, encodedPassword));
    }

    @Test
    public void checkIncorrectPassword() {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encodedPassword = bCryptPasswordEncoder.encode(PASSWORD);
        Assert.assertFalse("Password must don't match with encoded password",
                           bCryptPasswordEncoder.matches(PASSWORD + 1, encodedPassword));
    }
}