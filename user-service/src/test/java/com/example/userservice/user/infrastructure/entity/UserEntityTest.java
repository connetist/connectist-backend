package com.example.userservice.user.infrastructure.entity;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserEntityTest {



    @Test
    @DisplayName("User Entity 생성 테스트")
    void createUserEntity() {
        // given
        String id = "exId";
        String pw = "password";
        String email = "email";
        School school = School.KAIST;
        UserDegree degree = UserDegree.Doctor;
        UserSex sex = UserSex.MALE;
        UserMajor major = UserMajor.AI;
        UserStatus status = UserStatus.ABLE;
        String nickname = "nickname";
        Long createdAt = System.currentTimeMillis();

        // when
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPw(pw);
        userEntity.setEmail(email);
        userEntity.setSchool(school);
        userEntity.setDegree(degree);
        userEntity.setSex(sex);
        userEntity.setMajor(major);
        userEntity.setStatus(status);
        userEntity.setNickname(nickname);
        userEntity.setCreatedAt(createdAt);

        // then
        assertThat(userEntity.getId()).isEqualTo(id);
        assertThat(userEntity.getPw()).isEqualTo(pw);
        assertThat(userEntity.getEmail()).isEqualTo(email);
        assertThat(userEntity.getSchool()).isEqualTo(school);
        assertThat(userEntity.getDegree()).isEqualTo(degree);
        assertThat(userEntity.getSex()).isEqualTo(sex);
        assertThat(userEntity.getMajor()).isEqualTo(major);
        assertThat(userEntity.getStatus()).isEqualTo(status);
        assertThat(userEntity.getNickname()).isEqualTo(nickname);
        assertThat(userEntity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    @DisplayName("UserEntity => User : entity에서 domain 변환")
    void convertToUser() {
        // given
        String id = "exId";
        String pw = "password";
        String email = "email";
        School school = School.KAIST;
        UserDegree degree = UserDegree.Doctor;
        UserSex sex = UserSex.MALE;
        UserMajor major = UserMajor.AI;
        UserStatus status = UserStatus.ABLE;
        String nickname = "nickname";
        Long createdAt = System.currentTimeMillis();

        // when
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPw(pw);
        userEntity.setEmail(email);
        userEntity.setSchool(school);
        userEntity.setDegree(degree);
        userEntity.setSex(sex);
        userEntity.setMajor(major);
        userEntity.setStatus(status);
        userEntity.setNickname(nickname);
        userEntity.setCreatedAt(createdAt);

        User user = userEntity.toModel();

        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getPassword()).isEqualTo(userEntity.getPw());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getSchool()).isEqualTo(userEntity.getSchool());
        assertThat(user.getDegree()).isEqualTo(userEntity.getDegree());
        assertThat(user.getMajor()).isEqualTo(userEntity.getMajor());
        assertThat(user.getSex()).isEqualTo(userEntity.getSex());
        assertThat(user.getStatus()).isEqualTo(userEntity.getStatus());
        assertThat(user.getNickname()).isEqualTo(userEntity.getNickname());
        assertThat(user.getCreatedAt()).isEqualTo(userEntity.getCreatedAt());
    }

    @Test
    @DisplayName("User => UserEntity : domain 에서 entity 변환")
    void convertFromUser() {
        String id = "exId";
        String pw = "password";
        String email = "email";
        School school = School.KAIST;
        UserDegree degree = UserDegree.Doctor;
        UserSex sex = UserSex.MALE;
        UserMajor major = UserMajor.AI;
        UserStatus status = UserStatus.ABLE;
        String nickname = "nickname";
        Long createdAt = System.currentTimeMillis();

        User user = User.builder()
                .id(id)
                .pw(pw)
                .email(email)
                .school(school)
                .degree(degree)
                .sex(sex)
                .major(major)
                .status(status)
                .nickname(nickname)
                .createdAt(createdAt)
                .build();

        UserEntity userEntity = UserEntity.from(user);

        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getPassword()).isEqualTo(userEntity.getPw());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getSchool()).isEqualTo(userEntity.getSchool());
        assertThat(user.getDegree()).isEqualTo(userEntity.getDegree());
        assertThat(user.getMajor()).isEqualTo(userEntity.getMajor());
        assertThat(user.getSex()).isEqualTo(userEntity.getSex());
        assertThat(user.getStatus()).isEqualTo(userEntity.getStatus());
        assertThat(user.getNickname()).isEqualTo(userEntity.getNickname());
        assertThat(user.getCreatedAt()).isEqualTo(userEntity.getCreatedAt());
    }
}