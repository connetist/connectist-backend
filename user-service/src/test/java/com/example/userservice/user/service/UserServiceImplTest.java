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
import com.example.userservice.user.service.token.JwtTokenService;
import com.example.userservice.user.service.token.JwtTokenServiceImpl;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.user.service.user.join.JoinUserService;
import com.example.userservice.user.service.user.join.JoinUserServiceImpl;
import com.example.userservice.user.service.user.UserServiceImpl;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.clock.SystemClockHolder;
import com.example.userservice.util.exception.code.GlobalException;
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
    private JwtTokenService jwtTokenService;
    @Mock
    private JoinUserService joinUserService;
    @Spy
    private PasswordEncoder passwordEncoder;

    private final String newDummyPassword = "new";
    private final String oldDummyPassword = "old";
    private final String email = "test@test.com";

    private final ClockHolder clockHolder = new SystemClockHolder();
    private final IdGenerator idGenerator = new IdGeneratorByUUID();

    @BeforeEach
    void init() {

        // JoinUserRepository
        JoinUserRepository joinUserRepository = new FakeUserDigitRepository();
        // userRepository
        userRepository = new FakeUserRepository();

        // password Encoder
        passwordEncoder = spy(BCryptPasswordEncoder.class);
        doReturn(newDummyPassword).when(passwordEncoder).encode(any(String.class));

        // join user service
        joinUserService = mock(JoinUserServiceImpl.class);
        UserJoin userJoin = UserJoin.builder()
                .email(email)
                .school(School.GIST)
                .status(UserStatus.ABLE)
                .build();
        when(joinUserService.emailCertificationBeforeJoin(email)).thenReturn(userJoin);

        // jwt token service
        jwtTokenService = mock(JwtTokenServiceImpl.class);

        userService = UserServiceImpl.builder()
                .clockHolder(clockHolder)
                .userRepository(userRepository)
                .jwtTokenService(jwtTokenService)
                .passwordEncoder(passwordEncoder)
                .idGenerator(idGenerator)
                .joinUserService(joinUserService)
                .joinUserRepository(joinUserRepository)
                .build();
    }

    @Test
    @DisplayName("유저를 생성 테스트를 성공한다.")
    void createTest() {

        //given
        UserCreateRequest create = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(3)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();

        // when
        User user = userService.create(create);
        User userFind = userRepository.findById(user.getId()).orElseThrow();

        // then
        Assertions.assertThat(user.getEmail()).isEqualTo(create.getEmail());
        Assertions.assertThat(user.getPassword()).isEqualTo(newDummyPassword);
        Assertions.assertThat(user.getCreatedAt()).isInstanceOf(Long.class);
        // 저장
        Assertions.assertThat(user.getId()).isEqualTo(userFind.getId());
        Assertions.assertThat(user.getPassword()).isEqualTo(userFind.getPassword());
    }

    @Test
    @DisplayName("유저를 생성 테스트를 실패한다 : 중복 유저")
    void createTestFail() {
        //given
        UserCreateRequest create = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(3)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(create);

        // when / then
        Assertions.assertThatThrownBy(() -> userService.create(create))
                .isInstanceOf(GlobalException.class);
    }


    @Test
    @DisplayName("유저 로그인에 성공한다.")
    void userLoginTest() {
        //given
        when(jwtTokenService.accessToken(any(User.class))).thenReturn("test access token");
        when(jwtTokenService.refreshToken(any(User.class))).thenReturn(Token.builder().refreshToken("test refresh token").build());
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
//        doReturn(false).when(passwordEncoder).matches(eq(oldDummyPassword), anyString());



        UserLoginRequest userLoginRequest = new UserLoginRequest(email, newDummyPassword);
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);


        // when
        UserWithToken userWithToken = userService.login(userLoginRequest);

        Assertions.assertThat(userWithToken.getUser().getEmail()).isEqualTo(userLoginRequest.getEmail());
        Assertions.assertThat(userWithToken.getAccess()).isInstanceOf(String.class);
        Assertions.assertThat(userWithToken.getRefresh()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("유저 로그인에 실패한다. : 비밀번호 오류")
    void userLoginTestFailPassword() {
        //given
        doReturn(false).when(passwordEncoder).matches(anyString(), anyString());

        UserLoginRequest userLoginRequest = new UserLoginRequest(email, newDummyPassword);
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);

        // when
        Assertions.assertThatThrownBy(() -> userService.login(userLoginRequest))
                .isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("유저 로그인에 실패한다. : 이메일 오류")
    void userLoginTestFailEmail() {
        //given
        UserLoginRequest userLoginRequest = new UserLoginRequest("fakeEmail", newDummyPassword);
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);

        // when
        Assertions.assertThatThrownBy(() -> userService.login(userLoginRequest))
                .isInstanceOf(GlobalException.class);
    }

    // 업데이트
    @Test
    @DisplayName("유저 업데이트를 진행한다.")
    void userUpdateTest() {
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);

        UserUpdateRequest userUpdate = UserUpdateRequest.builder()
                .email(email)
                .password(newDummyPassword)
                .degree(UserDegree.Doctor)
                .sex(UserSex.MALE)
                .major(UserMajor.Chemistry)
                .nickname("New dummy")
                .build();

        // when
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
    void userDeleteTest() {
        // given
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);

        UserDeleteRequest udr = new UserDeleteRequest();
        udr.setEmail(email);
        udr.setPassword(newDummyPassword);
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());

        userService.delete(udr);

        Assertions.assertThat(userRepository.findByEmail(email)).isNotPresent();
    }

    @Test
    @DisplayName("유저를 email 검색한다.")
    void userFindByEmailTest() {
        // given
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);
        // when
        User userFind = userService.findByEmail(email);
        // then
        Assertions.assertThat(userFind.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("유저를 id로 검색한다.")
    void userFindByIdTest() {
        // given
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        User user = userService.create(createRequest);
        // when
        User userFind = userService.findById(user.getId());
        // then
        Assertions.assertThat(userFind.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(userFind.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저 id 검색 실패")
    void userFindByIdTestFail() {
        // given
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        User user = userService.create(createRequest);
        String userId = user.getId();

        // when
        Assertions.assertThatThrownBy
                        (() -> userService.findById(userId + " dd"))
                .isInstanceOf(GlobalException.class);
    }
    @Test
    @DisplayName("유저 email 검색 실패")
    void userFindByEmailTestFail() {
        // given
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .pw(oldDummyPassword)
                .email(email)
                .degree(2)
                .sex(1)
                .major(1)
                .nickname("dummy")
                .build();
        userService.create(createRequest);

        // when
        Assertions.assertThatThrownBy
                        (() -> userService.findByEmail(email + " dd"))
                .isInstanceOf(GlobalException.class);
    }


}