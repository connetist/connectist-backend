package com.example.userservice.user.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLogin {

    private String email;
    private String password;
}
