package com.example.userservice.user.domain;

import com.example.userservice.user.dto.request.UserUpdateRequest;
import com.example.userservice.user.domain.user.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserUpdate {

    // 가변 파라미터
    private final String email;
    private final String password;
    private final School school;
    private final UserDegree degree;
    private final UserSex sex;
    private final UserMajor major;
    private final String nickname;

    public static UserUpdate fromWithRequest(UserUpdateRequest request) {
        return UserUpdate.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .school(request.getSchool())
                .degree(request.getDegree())
                .sex(request.getSex())
                .major(request.getMajor())
                .nickname(request.getNickname())
                .build();
    }



}
