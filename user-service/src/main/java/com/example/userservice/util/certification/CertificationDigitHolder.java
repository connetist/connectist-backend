package com.example.userservice.util.certification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CertificationDigitHolder implements CertificationHolder{
    @Override
    public String random() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());

        int num = generator.nextInt(100000000) ;
        return String.valueOf(num).substring(0,6);
    }
}
