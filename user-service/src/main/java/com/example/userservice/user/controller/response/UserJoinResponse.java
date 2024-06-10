package com.example.userservice.user.controller.response;

import com.example.userservice.user.domain.UserSchool;
import com.example.userservice.user.domain.join.JoinUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserJoinResponse {

    public String email;
    public UserSchool userSchool;

    public static UserJoinResponse from(JoinUser joinUser) {
        return UserJoinResponse.builder()
                .email(joinUser.getEmail())
                .userSchool(joinUser.getUserSchool())
                .build();
    }

}
