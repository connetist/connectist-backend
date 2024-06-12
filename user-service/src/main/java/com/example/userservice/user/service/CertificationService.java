package com.example.userservice.user.service;

import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.service.port.MailSender;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    private final static String TITLE = "Please certify your email address";
    private final static String CONTENT = "Please click the following link to certify your email address: ";

    public void send(String email, String certificationCode) {
        String certificationUrl = generateCertificationUrl(email, certificationCode);
        mailSender.send(email, TITLE, CONTENT + certificationUrl);
    }

    private String generateCertificationUrl(String email, String certificationCode) {
        return "http://localhost:8080/api/users/" + email + "/verify?certificationCode=" + certificationCode;
    }
}
