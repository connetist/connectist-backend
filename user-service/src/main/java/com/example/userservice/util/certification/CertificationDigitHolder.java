package com.example.userservice.util.certification;

import org.springframework.stereotype.Component;

@Component
public class CertificationDigitHolder implements CertificationHolder{
    @Override
    public String random() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return Integer.toString(generator.nextInt(1000000) % 1000000);
    }
}
