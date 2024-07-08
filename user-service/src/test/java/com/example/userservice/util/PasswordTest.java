package com.example.userservice.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncode() {
        String original = "admin";
        String encoded = passwordEncoder.encode(original);
        System.out.println(encoded);
    }
}
