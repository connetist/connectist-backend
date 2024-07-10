package com.example.userservice.config;

import com.example.userservice.util.certification.CertificationDigitHolder;
import com.example.userservice.util.certification.CertificationHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // certification : UUID => 6자리 숫자로 변경
    @Bean
    public CertificationHolder certificationHolder() {
        return new CertificationDigitHolder();
    }

}
