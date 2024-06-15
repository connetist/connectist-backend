package com.example.userservice.user.controller.request;

import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.domain.user.UserDegree;
import com.example.userservice.user.domain.user.UserMajor;
import com.example.userservice.user.domain.user.UserSex;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @NonNull private String email;
    @NonNull private String pw;
    @NonNull private School school;
    @NonNull private UserDegree degree;
    @NonNull private UserSex sex;
    @NonNull private UserMajor major;
    @NonNull private String nickname;
}
