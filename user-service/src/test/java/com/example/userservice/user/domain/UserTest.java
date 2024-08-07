package com.example.userservice.user.domain;

import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.value.UserDegree;
import com.example.userservice.user.domain.user.value.UserMajor;
import com.example.userservice.user.domain.user.value.UserSex;
import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.clock.SystemClockHolder;
import com.example.userservice.util.id.IdGenerator;
import com.example.userservice.util.id.IdGeneratorByUUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private final ClockHolder clockHolder = new SystemClockHolder();
    private final IdGenerator idGenerator = new IdGeneratorByUUID();
    private final MockPasswordEncode mockPasswordEncode = new MockPasswordEncode();

    @Test
    @DisplayName("유저 도메인을 생성한다.")
    void 유저_도메인을_생성한다() {
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .pw("password")
                .email("example@example.com")
                .degree(1)
                .sex(1)
                .major(1)
                .nickname("test")
                .build();

        UserJoin userJoin = UserJoin.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.create(userCreate, userJoin, clockHolder, idGenerator);
        Long nowUnixTime = clockHolder.getNowUnixTime();

        assertThat(user.getEmail()).isEqualTo(userJoin.getEmail());
        assertThat(user.getPassword()).isEqualTo(userCreate.getPw());
        assertThat(user.getSchool()).isEqualTo(userJoin.getSchool());
        assertThat(user.getDegree()).isEqualTo(userCreate.findDegree());

        // id가 UUID 로 생성되는지 확인
        Pattern UUID_REGEX =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        assertThat(UUID_REGEX.matcher(user.getId()).matches()).isTrue();
        // 생성 시간 일치하는지 확인
        assertThat(user.getCreatedAt()).isEqualTo(nowUnixTime);

    }

    @Test
    @DisplayName("유저 도메인을 업데이트한다.")
    void 유저_도메인을_업데이트한다() {
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .pw("password")
                .email("example@example.com")
                .degree(1)
                .sex(1)
                .major(1)
                .nickname("test")
                .build();


        UserJoin userJoin = UserJoin.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.create(userCreate, userJoin, clockHolder, idGenerator);

        UserUpdateRequest userUpdate = UserUpdateRequest.builder()
                .email("example11@example.com")
                .password("example11")
                .degree(UserDegree.Master)
                .sex(UserSex.FEMALE)
                .major(UserMajor.Biology)
                .nickname("example Nickname")
                .build();

        User updatedUser = User.update(user, userUpdate);

        // 업데이트 후에도 같아야 하는 것
        assertThat(user.getId()).isEqualTo(updatedUser.getId());
        assertThat(user.getCreatedAt()).isEqualTo(updatedUser.getCreatedAt());

        // 업데이트 후에 달라야 하는 것
        assertThat(user.getEmail()).isNotEqualTo(updatedUser.getEmail());
        assertThat(user.getMajor()).isNotEqualTo(updatedUser.getMajor());
        assertThat(user.getSex()).isNotEqualTo(updatedUser.getSex());
        assertThat(user.getDegree()).isNotEqualTo(updatedUser.getDegree());
        assertThat(user.getNickname()).isNotEqualTo(updatedUser.getNickname());
    }

    @Test
    @DisplayName("유저 도메인의 비밀번호를 변경한다.")
    void 유저_도메인의_비밀번호를_변경한다() {
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .pw("password")
                .email("example@example.com")
                .degree(1)
                .sex(1)
                .major(1)
                .nickname("test")
                .build();


        UserJoin userJoin = UserJoin.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.create(userCreate, userJoin, clockHolder, idGenerator);

        String encodedPassword = mockPasswordEncode.encode(user.getPassword());
        User userEncoded = User.encodePw(user, encodedPassword);

        assertThat(user.getPassword()).isNotEqualTo(userEncoded.getPassword());
    }

    private static class MockPasswordEncode implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return new StringBuilder(rawPassword).reverse().toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }

}