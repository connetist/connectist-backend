package com.example.userservice.user.service;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.token.Token;
import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.user.dto.response.token.UserWithToken;
import com.example.userservice.user.domain.user.value.*;
import com.example.userservice.user.dto.request.user.UserDeleteRequest;
import com.example.userservice.user.dto.request.user.UserLoginRequest;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
import com.example.userservice.user.mock.FakeUserDigitRepository;
import com.example.userservice.user.mock.FakeUserRepository;
import com.example.userservice.user.service.token.JwtTokenServiceImpl;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.user.service.user.join.JoinUserServiceImpl;
import com.example.userservice.user.service.user.UserServiceImpl;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.clock.SystemClockHolder;
import com.example.userservice.util.id.IdGenerator;
import com.example.userservice.util.id.IdGeneratorByUUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    private UserRepository userRepository;

    @Mock
    private JwtTokenServiceImpl jwtTokenService;
    @Mock
    private JoinUserServiceImpl joinUserService;
    @Spy
    private PasswordEncoder passwordEncoder;

    private final String newDummyPassword = "new";
    private final String oldDummyPassword = "old";
    private final String email = "test@test.com";


    @BeforeEach
    void init() {
        ClockHolder clockHolder = new SystemClockHolder();
        IdGenerator idGenerator = new IdGeneratorByUUID();
        JoinUserRepository joinUserRepository = new FakeUserDigitRepository();

        userRepository = new FakeUserRepository();

        PasswordEncoder spyPasswordEncoder = new BCryptPasswordEncoder();
        passwordEncoder = spy(spyPasswordEncoder);

        doReturn(newDummyPassword).when(passwordEncoder).encode(any(String.class));

        userService = UserServiceImpl.builder()
                .clockHolder(clockHolder)
                .userRepository(userRepository)
                .jwtTokenService(jwtTokenService)
                .passwordEncoder(passwordEncoder)
                .idGenerator(idGenerator)
                .joinUserService(joinUserService)
                .joinUserRepository(joinUserRepository)
                .build();

        UserJoin userJoin = UserJoin.builder()
                .email(email)
                .school(School.GIST)
                .status(UserStatus.ABLE)
                .build();
        doReturn(userJoin).when(joinUserService).emailCertificationBeforeJoin("test@test.com");

        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email("test@test.com")
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();

        userService.create(createRequest);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void 유저를_생성한다() {

        UserJoin userJoin = UserJoin.builder()
                .email("test11@test.com")
                .school(School.GIST)
                .status(UserStatus.ABLE)
                .build();
        doReturn(userJoin).when(joinUserService).emailCertificationBeforeJoin(any(String.class));

        UserCreateRequest create = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email("test11@test.com")
                .degree(3)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();

        User user = userService.create(create);
        User userFind = userRepository.findById(user.getId()).orElseThrow();

        Assertions.assertThat(user.getEmail()).isEqualTo(create.getEmail());
        Assertions.assertThat(user.getSchool()).isEqualTo(userJoin.getSchool());
        Assertions.assertThat(user.getPassword()).isEqualTo(newDummyPassword);
        Assertions.assertThat(user.getCreatedAt()).isInstanceOf(Long.class);

        // 저장되는지
        Assertions.assertThat(user.getId()).isEqualTo(userFind.getId());
    }

    @Test
    @DisplayName("유저 로그인")
    void 유저_로그인() {
        doReturn(true).when(passwordEncoder).matches(any(String.class), any(String.class));

        UserLoginRequest userLoginRequest = new UserLoginRequest(
                "test@test.com", newDummyPassword
        );
        when(jwtTokenService.accessToken(any(User.class))).thenReturn("test access token");
        when(jwtTokenService.refreshToken(any(User.class))).thenReturn(Token.builder().refreshToken("test refresh token").build());

        UserWithToken userWithToken = userService.login(userLoginRequest);

        Assertions.assertThat(userWithToken.getUser().getEmail()).isEqualTo(userLoginRequest.getEmail());
        Assertions.assertThat(userWithToken.getAccess()).isInstanceOf(String.class);
        Assertions.assertThat(userWithToken.getRefresh()).isInstanceOf(String.class);
    }

    // 업데이트
    @Test
    @DisplayName("유저 업데이트를 진행한다.")
    void 유저_업데이트() {
        UserUpdateRequest userUpdate = UserUpdateRequest.builder()
                .email(email)
                .password(newDummyPassword)
                .degree(UserDegree.Doctor)
                .sex(UserSex.MALE)
                .major(UserMajor.ChemiStry)
                .nickname("New dummy")
                .build();

        userService.update(userUpdate);

        User user = userRepository.findByEmail(userUpdate.getEmail()).orElseThrow();
        Assertions.assertThat(user.getEmail()).isEqualTo(userUpdate.getEmail());
        Assertions.assertThat(user.getDegree()).isEqualTo(userUpdate.getDegree());
        Assertions.assertThat(user.getMajor()).isEqualTo(userUpdate.getMajor());
        Assertions.assertThat(user.getNickname()).isEqualTo(userUpdate.getNickname());
        Assertions.assertThat(user.getPassword()).isEqualTo(userUpdate.getPassword());
    }

    @Test
    @DisplayName("유저를 삭제한다.")
    void 유저를_삭제한다() {
        UserDeleteRequest udr = new UserDeleteRequest();
        udr.setEmail(email);
        udr.setPassword(newDummyPassword);
        doReturn(true).when(passwordEncoder).matches(any(String.class), any(String.class));

        userService.delete(udr);
        Assertions.assertThat(userRepository.findByEmail(email)).isNotPresent();
    }

    @Test
    @DisplayName("유저를 검색한다.")
    void 유저를_검색한다() {
        User byEmail = userService.findByEmail(email);
        Assertions.assertThat(byEmail.getEmail()).isEqualTo(email);
    }
}