package com.example.userservice.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final String email;
    private final UserSchool userSchool;
    private final String certificationCode;




    //User Builder
    @Builder
    public User(String email, String certificationCode, UserSchool userSchool, UserStatus userStatus) {
        this.email = email;
        this.userSchool = userSchool;
        this.certificationCode = certificationCode;
    }

//    public static User from(UserCreate userCreate) {
//        return User.builder()
//                .email(userCreate.getEmail())
//                .certificationCode(userCreate.get);
//    }
}
