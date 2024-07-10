package com.example.userservice.user.domain.user;

import com.example.userservice.user.domain.user.value.*;
import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.id.IdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class User {

    private final String id;
    private final String password;
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
        this.password = pw;
        this.degree = degree;
        this.sex = sex;
        this.major = major;
        this.nickname = nickname;
        this.status = status;
        this.createdAt = createdAt;
    }

    //UserUpdate롤 이용한 user제작
    public static User update(User user, UserUpdateRequest userUpdateRequest) {
        return User.builder()
                .email(userUpdateRequest.getEmail())
                .id(user.getId())
                .pw(userUpdateRequest.getPassword())
                .degree(userUpdateRequest.getDegree())
                .sex(userUpdateRequest.getSex())
                .major(userUpdateRequest.getMajor())
                .status(user.getStatus())
                .nickname(userUpdateRequest.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // static으로 작성하는 이점이 있을까요?
    public static User encodePw(User user, String encodedPw) {
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

    public static User create(UserCreateRequest userCreateRequest,UserJoin userJoin, ClockHolder clockHolder, IdGenerator idGenerator) {
        return User.builder()
                .email(userJoin.getEmail())
                .id(idGenerator.generate())
                .pw(userCreateRequest.getPw())
                .school(userJoin.getSchool())
                .degree(userCreateRequest.findDegree())
                .sex(userCreateRequest.findUserSex())
                .major(userCreateRequest.findUserMajor())
                .status(userJoin.getStatus())
                .nickname(userCreateRequest.getNickname())
                .createdAt(clockHolder.getNowUnixTime())
                .build();
    }
}
