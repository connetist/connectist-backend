package com.example.userservice.user.service;

import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
import com.example.userservice.user.mock.FakeUserDigitRepository;
import com.example.userservice.user.mock.FakeUserRepository;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.user.service.user.mail.UserCertificationService;
import com.example.userservice.user.service.user.join.JoinUserServiceImpl;
import com.example.userservice.util.certification.CertificationDigitHolder;
import com.example.userservice.util.certification.CertificationHolder;

import com.example.userservice.util.certification.email.EmailCertification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserJoinServiceImplTest {

    private JoinUserServiceImpl joinUserService;
    private JoinUserRepository joinUserRepository;

    @Mock
    UserCertificationService userCertificationService;

    @BeforeEach
    void init() {
        CertificationHolder certificationHolder = new CertificationDigitHolder();
        joinUserRepository = new FakeUserDigitRepository();
        EmailCertification emailCertification = new EmailCertification();
        UserRepository userRepository = new FakeUserRepository();
        userCertificationService = mock(UserCertificationService.class);

        joinUserService = JoinUserServiceImpl.builder()
                .certificationHolder(certificationHolder)
                .joinUserRepository(joinUserRepository)
                .emailCertification(emailCertification)
                .userCertificationService(userCertificationService)
                .userRepository(userRepository)
                .build();



    }


    // 이미 있는 사용자인지 확인한다.
    @Test
    void 이미_있는_사용자인지_확인한다() {
        String email = "test@test.com";
        String newEmail = "test1@test.com";
        joinUserRepository.save(UserJoin.builder().email(email).build());
        boolean value = joinUserService.checkEmailBeforeCertification(newEmail);

        Assertions.assertThat(email).isNotEqualTo(newEmail);
        Assertions.assertThat(value).isTrue();
    }

    // 사용자에게 확인 메일을 보낸다.
    @Test
    void 사용자의_확인_코드를_만든다() {
        UserJoinRequest request = UserJoinRequest.builder()
                .email("test@gm.gist.ac.kr")
                .school(3)
                .build();

        UserJoin join = joinUserService.join(request);
        Assertions.assertThat(join.getCertificationCode()).isInstanceOf(String.class);
    }

    // 이메일 인증한다.
    @Test
    void 사용자의_이메일을_인증한다() {
        String certificationCode = "test";
        UserJoin userJoin = UserJoin.builder()
                .email("test@test.com")
                .school(School.GIST)
                .certificationCode(certificationCode)
                .status(UserStatus.PENDING)
                .build();
        joinUserRepository.save(userJoin);

        UserJoinCertificationRequest request = UserJoinCertificationRequest.builder()
                .email("test@test.com")
                .certificationCode(certificationCode)
                .build();

        UserJoin certification = joinUserService.certification(request);
        Assertions.assertThat(certification.getStatus()).isEqualTo(UserStatus.ABLE);

    }


}