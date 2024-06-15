package com.example.userservice.user.domain;

import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.*;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.id.IdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class User {

    private final String id;
    private final String pw;
    private final String email;
    private final School school;
    private final UserDegree degree;
    private final UserSex sex;
    private final UserMajor major;
    private final UserStatus status;
    private final String nickname;
    private final Long createdAt;


    //User Builder
    @Builder
    public User(String id, String pw, String email, School school, UserDegree degree, UserSex sex, UserMajor major, String nickname, UserStatus status, Long createdAt) {
        this.email = email;
        this.school = school;
        this.id = id;
        this.pw = pw;
        this.degree = degree;
        this.sex = sex;
        this.major = major;
        this.nickname = nickname;
        this.status = status;
        this.createdAt = createdAt;
    }

    // userCreate를 이용한 user제작
    public static User fromAfterCertification(UserCreate userCreate, JoinUser joinUser, ClockHolder clockHolder, IdGenerator idGenerator) {
        return User.builder()
                .email(joinUser.getEmail())
                .id(idGenerator.generate())
                .pw(userCreate.getPw())
                .school(joinUser.getSchool())
                .degree(userCreate.getDegree())
                .sex(userCreate.getSex())
                .major(userCreate.getMajor())
                .status(joinUser.getStatus())
                .nickname(userCreate.getNickname())
                .createdAt(clockHolder.getNowUnixTime())
                .build();
    }

    //UserUpdate롤 이용한 user제작
    public static User fromWithUserUpdate(User user, UserUpdate userUpdate) {
        return User.builder()
                .email(userUpdate.getEmail())
                .id(user.getId())
                .pw(userUpdate.getPw())
                .school(userUpdate.getSchool())
                .degree(userUpdate.getDegree())
                .sex(userUpdate.getSex())
                .major(userUpdate.getMajor())
                .status(user.getStatus())
                .nickname(userUpdate.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User encodePw(User user, String encodedPw) {
        return User.builder()
                .email(user.getEmail())
                .id(user.getId())
                .pw(encodedPw)
                .school(user.getSchool())
                .degree(user.getDegree())
                .sex(user.getSex())
                .major(user.getMajor())
                .status(user.getStatus())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
