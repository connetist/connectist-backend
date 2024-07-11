package com.example.userservice.user.dto.request.user;

import com.example.userservice.user.domain.user.value.UserDegree;
import com.example.userservice.user.domain.user.value.UserMajor;
import com.example.userservice.user.domain.user.value.UserSex;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String password;
    private UserDegree degree;
    private UserSex sex;
    private UserMajor major;
    private String nickname;
}
