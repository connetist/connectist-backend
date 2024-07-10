package com.example.userservice.user.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginRequest {

    private String email;
    private String password;
}
