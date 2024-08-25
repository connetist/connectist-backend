package com.example.userservice.user.service.user.join;

import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
import com.example.userservice.user.mock.FakeUserDigitRepository;
import com.example.userservice.user.service.user.mail.UserCertificationService;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.util.certification.email.EmailCertification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoinUserServiceImplTest {

    private JoinUserService joinUserService;
    private JoinUserRepository joinUserRepository;
    private CertificationHolder certificationHolder;
    private UserCertificationService userCertificationService;
    private EmailCertification emailCertification;
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        joinUserRepository = new FakeUserDigitRepository();
        joinUserService = new JoinUserServiceImpl(
                certificationHolder, joinUserRepository, userCertificationService, emailCertification, userRepository
        );
    }

    @Test
    void 이메일6자리코드로인증하기() {
        // given
        UserJoinCertificationRequest userJoinCertificationRequest = new UserJoinCertificationRequest("test", "333333");
        UserJoin beforeJoinMock = new UserJoin("test", School.KAIST, "333333", UserStatus.PENDING);
        joinUserRepository.save(beforeJoinMock);

        // when
        UserJoin afterJoinMock = joinUserService.certification(userJoinCertificationRequest);

        // then
        Assertions.assertThat(afterJoinMock.getStatus()).isEqualTo(UserStatus.ABLE);
        Assertions.assertThat(afterJoinMock.getEmail()).isEqualTo("test");
    }

    @Test
    void 유효한email이없다() {
        UserJoinCertificationRequest userJoinCertificationRequest = new UserJoinCertificationRequest("exception", "333333");
        UserJoin beforeJoinMock = new UserJoin("test", School.KAIST, "333333", UserStatus.PENDING);
        joinUserRepository.save(beforeJoinMock);


        Assertions.assertThatThrownBy(() -> joinUserService.certification(userJoinCertificationRequest)).isInstanceOf(RuntimeException.class);
    }


}