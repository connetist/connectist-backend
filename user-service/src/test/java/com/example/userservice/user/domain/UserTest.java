package com.example.userservice.user.domain;

import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.domain.user.UserDegree;
import com.example.userservice.user.domain.user.UserMajor;
import com.example.userservice.user.domain.user.UserSex;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.clock.SystemClockHolder;
import com.example.userservice.util.id.IdGenerator;
import com.example.userservice.util.id.IdGeneratorByUUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
        UserCreate userCreate = UserCreate.builder()
                .pw("password")
                .email("example@example.com")
                .degree(UserDegree.Bachelor)
                .sex(UserSex.MALE)
                .major(UserMajor.AI)
                .nickname("test")
                .build();

        JoinUser joinUser = JoinUser.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.fromAfterCertification(userCreate, joinUser, clockHolder, idGenerator);
        Long nowUnixTime = clockHolder.getNowUnixTime();

        assertThat(user.getEmail()).isEqualTo(joinUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(userCreate.getPw());
        assertThat(user.getSchool()).isEqualTo(joinUser.getSchool());
        assertThat(user.getDegree()).isEqualTo(userCreate.getDegree());

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
        UserCreate userCreate = UserCreate.builder()
                .pw("password")
                .email("example@example.com")
                .degree(UserDegree.Bachelor)
                .sex(UserSex.MALE)
                .major(UserMajor.AI)
                .nickname("test")
                .build();

        JoinUser joinUser = JoinUser.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.fromAfterCertification(userCreate, joinUser, clockHolder, idGenerator);

        UserUpdate userUpdate = UserUpdate.builder()
                .email("example11@example.com")
                .password("example11")
                .school(School.GIST)
                .degree(UserDegree.Master)
                .sex(UserSex.FEMALE)
                .major(UserMajor.Biology)
                .nickname("example Nickname")
                .build();

        User updatedUser = User.fromWithUserUpdate(user, userUpdate);

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
        UserCreate userCreate = UserCreate.builder()
                .pw("password")
                .email("example@example.com")
                .degree(UserDegree.Bachelor)
                .sex(UserSex.MALE)
                .major(UserMajor.AI)
                .nickname("test")
                .build();

        JoinUser joinUser = JoinUser.builder()
                .email("example@example.com")
                .school(School.UNIST)
                .certificationCode("333333")
                .build();

        User user = User.fromAfterCertification(userCreate, joinUser, clockHolder, idGenerator);

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