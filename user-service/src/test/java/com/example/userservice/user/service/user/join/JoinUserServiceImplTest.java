package com.example.userservice.user.service.user.join;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
import com.example.userservice.user.mock.FakeUserDigitRepository;
import com.example.userservice.user.service.user.mail.UserCertificationService;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.util.certification.email.EmailCertification;
import com.example.userservice.util.exception.code.GlobalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Optional;


class JoinUserServiceImplTest {

    private JoinUserService joinUserService;
    private JoinUserRepository joinUserRepository;
    private EmailCertification emailCertification;

    @Mock
    private CertificationHolder certificationHolder;
    @Mock
    private UserCertificationService mockUserCertificationService;
    @Mock
    private UserRepository mockUserRepository;


    @BeforeEach
    void init() {
        mockUserCertificationService = mockCertificationServiceInit();
        mockUserRepository = mockUserRepositoryInit();
        certificationHolder = mockCertificationHolderInit();
        joinUserRepository = new FakeUserDigitRepository();
        joinUserService = new JoinUserServiceImpl(
                certificationHolder, joinUserRepository, mockUserCertificationService, emailCertification, mockUserRepository
        );
    }

    private UserCertificationService mockCertificationServiceInit() {
        UserCertificationService mockCertificationService = Mockito.mock(UserCertificationService.class);
        return mockCertificationService;
    }

    private static CertificationHolder mockCertificationHolderInit() {
        CertificationHolder mockCertificationHolder = Mockito.mock(CertificationHolder.class);
        Mockito.when(mockCertificationHolder.random()).thenReturn("000000");
        return mockCertificationHolder;
    }

    private static UserRepository mockUserRepositoryInit() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findByEmail("exist")).thenReturn(Optional.of(new User(null, null, "exist", null, null, null, null, null, null, null)));
        Mockito.when(mockUserRepository.findByEmail("no-exist")).thenReturn(Optional.empty());
        return mockUserRepository;
    }

    @Test
    @DisplayName("중복 유저 검사 -> 중복 유저 userRepository애 있을 경우")
    void checkEmailBeforeCertificationFailUserRepository() {
        String email = "exist";
        Assertions
                .assertThatThrownBy(() ->
                        joinUserService.emailCertificationBeforeJoin(email))
                .isInstanceOf(GlobalException.class);
    }
    @Test
    @DisplayName("중복 유저 검사 -> 중복 유저 JoinUserRepository에 있을 경우")
    void checkEmailBeforeCertificationFailJoinUserRepository() {
        String email = "exist";
        UserJoinRequest userJoinRequest = new UserJoinRequest(email, 3);

        joinUserRepository.save(UserJoin.from(userJoinRequest, certificationHolder));

        Assertions
                .assertThatThrownBy(() -> joinUserService.emailCertificationBeforeJoin(email))
                .isInstanceOf(GlobalException.class);
    }
    @Test
    @DisplayName("중복 유저 검사 -> 중복 유저가 없을 경우")
    void checkEmailBeforeCertificationSuccess() {
        String email = "no-exist";
        boolean checkResult = joinUserService.checkEmailBeforeCertification(email);
        Assertions.assertThat(checkResult).isTrue();
    }

    @Test
    @DisplayName("6자리 인증코드 발송")
    void joinTestSuccess() {
        // given
        String email = "exist";
        UserJoinRequest userJoinRequest = new UserJoinRequest(email, 3);

        // when
        UserJoin joinedUser = joinUserService.join(userJoinRequest);

        //then
        UserJoin userJoin = joinUserRepository.findByEmail(joinedUser.getEmail()).get();
        Assertions.assertThat(userJoin).isNotNull();

        Assertions.assertThat(userJoinRequest.getEmail()).isEqualTo(joinedUser.getEmail());
        Assertions.assertThat(userJoinRequest.getSchool()).isEqualTo(joinedUser.getSchool());
        Assertions.assertThat(joinedUser.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(joinedUser.getCertificationCode()).isEqualTo("000000");
    }

    @Test
    @DisplayName("6자리 인증코드 발송 성공 -> 이미 인증을 시도한 이메일일경우")
    void joinUserTestFail() {
        // given
        String email = "exist";
        UserJoinRequest userJoinRequest = new UserJoinRequest(email, 3);
        joinUserRepository.save(UserJoin.from(userJoinRequest, certificationHolder));
        // when
        UserJoin duplicateUser = joinUserService.join(userJoinRequest);
        // then
        Assertions.assertThat(duplicateUser.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(duplicateUser.getEmail()).isEqualTo(userJoinRequest.getEmail());
        Assertions.assertThat(duplicateUser.getCertificationCode()).isEqualTo("000000");
    }

    @Test
    @DisplayName("6자리 코드로 인증하기")
    void emailCertificationTest() {
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