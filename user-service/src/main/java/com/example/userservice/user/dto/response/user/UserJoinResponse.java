package com.example.userservice.user.dto.response.user;

import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.domain.user.value.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserJoinResponse {

    public String email;
    public School userSchool;
    public UserStatus status;

    public static UserJoinResponse from(UserJoin userJoin) {
        return UserJoinResponse.builder()
                .email(userJoin.getEmail())
                .userSchool(userJoin.getSchool())
                .status(userJoin.getStatus())
                .build();
    }



}
