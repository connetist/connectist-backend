package com.example.userservice.user.controller.response;

import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserJoinResponse {

    public String email;
    public School userSchool;
    public UserStatus status;

    public static UserJoinResponse from(JoinUser joinUser) {
        return UserJoinResponse.builder()
                .email(joinUser.getEmail())
                .userSchool(joinUser.getSchool())
                .status(joinUser.getStatus())
                .build();
    }

}
