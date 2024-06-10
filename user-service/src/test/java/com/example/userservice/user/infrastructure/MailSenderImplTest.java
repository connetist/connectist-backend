package com.example.userservice.user.infrastructure;

import com.example.userservice.user.service.port.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MailSenderImplTest {

    private MailSender mailSender;
    private JavaMailSender javaMailSender;
    @BeforeEach
    void init() {
        javaMailSender = mock(JavaMailSender.class);
        mailSender = MailSenderImpl.builder()
                .javaMailSender(javaMailSender)
                .build();
    }

    @Test
    void 사용자에게매일보내기() {
        String email = "exampleconnectist@gamil.com";
        String title = "example";
        String content = "exampleContent";
        mailSender.send(email, title, content);
    }

}