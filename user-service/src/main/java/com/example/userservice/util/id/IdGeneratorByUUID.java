package com.example.userservice.util.id;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorByUUID implements IdGenerator{
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
