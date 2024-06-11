package com.example.userservice.user.service;

import com.example.userservice.user.service.port.MailSender;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    private final String title = "Please certify your email address";
    private final String content = "Please click the following link to certify your email address: ";

    public void send(String email, String certificationCode) {
        String certificationUrl = generateCertificationUrl(email, certificationCode);
        mailSender.send(email, title, content + certificationCode);
    }

    private String generateCertificationUrl(String email, String certificationCode) {
        return "http://localhost:8080/api/users/" + email + "/verify?certificationCode=" + certificationCode;
    }
}
