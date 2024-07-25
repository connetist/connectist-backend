package com.example.userservice.user.service.user.mail;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.mail.MailSender;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class UserCertificationService {

    private final MailSender mailSender;

    private static final String TITLE = "Please certify your email address";
    private static final String CONTENT = "Please click the following link to certify your email address: ";

    public void send(String email, String certificationCode) {
        try {
            String certificationUrl = generateCertificationUrl(email, certificationCode);
            mailSender.send(email, TITLE, CONTENT + certificationUrl);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateCertificationUrl(String email, String certificationCode) {
        return "http://localhost:9014/user-service/api/users/join/" + email + "/verify?certificationCode=" + certificationCode;
    }
}
