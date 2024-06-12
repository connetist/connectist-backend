package com.example.userservice.user.domain;

import com.example.userservice.user.domain.user.*;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.id.IdGenerator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    public static User from(UserCreate userCreate, ClockHolder clockHolder, IdGenerator idGenerator) {
        return User.builder()
                .email(userCreate.getEmail())
                .id(idGenerator.generate())
                .pw(userCreate.getPw())
                .school(userCreate.getSchool())
                .degree(userCreate.getDegree())
                .sex(userCreate.getSex())
                .major(userCreate.getMajor())
                .status(UserStatus.ABLE)
                .nickname(userCreate.getNickname())
                .createdAt(clockHolder.getNowUnixTime())
                .build();
    }

}
