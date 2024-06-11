package com.example.userservice.user.service;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.join.JoinUserCertification;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.infrastructure.MailSenderImpl;
import com.example.userservice.user.infrastructure.join.JoinUserConcurrentMapRepository;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;
import com.example.userservice.user.service.port.MailSender;
import com.example.userservice.util.certification.CertificationDigitHolder;
import com.example.userservice.util.certification.CertificationHolder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JoinUserServiceImplTest {

    private JoinUserService joinUserService;
    private CertificationHolder certificationHolder;
    private JoinUserRepository joinUserRepository;
    private CertificationService certificationService;
    private JavaMailSender javaMailSender;
    private MailSender mailSender;
    private JoinUserCreate joinUserCreate;
    @BeforeEach
    void init() {
        certificationHolder = mock(CertificationDigitHolder.class);
        joinUserRepository = mock(JoinUserConcurrentMapRepository.class);
        javaMailSender = mock(JavaMailSender.class);
        mailSender = MailSenderImpl.builder().javaMailSender(javaMailSender).build();
        certificationService = CertificationService.builder()
                .mailSender(mailSender)
                .build();
        joinUserService = JoinUserServiceImpl.builder()
                .certificationService(certificationService)
                .joinUserRepository(joinUserRepository)
                .certificationHolder(certificationHolder)
                .build();

        joinUserCreate = JoinUserCreate.builder()
                .email("khj010909@gmail.com")
                .school(3)
                .build();
    }

//    @Test
    void 사용자메일인증하기() {

        System.out.println("joinUserCreate.toString() = " + joinUserCreate.toString());
        JoinUser joinUser1 = joinUserService.join(joinUserCreate);

        System.out.println("joinUser1 = " + joinUser1.getEmail());

        JoinUserCertification joinUserCertification =
                JoinUserCertification.builder()
                        .email(joinUser1.getEmail())
                        .certificationCode(joinUser1.getCertificationCode())
                        .build();
        JoinUser joinUser2 = joinUserService.certification(joinUserCertification);


        Assertions.assertThat(joinUser1.getCertificationCode()).isEqualTo(joinUser2.getCertificationCode());
    }


}