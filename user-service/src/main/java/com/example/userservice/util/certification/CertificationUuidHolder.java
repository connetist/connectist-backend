package com.example.userservice.util.certification;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CertificationUuidHolder implements CertificationHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
