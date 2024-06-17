package com.example.userservice.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserLogin {

    private String email;
    private String password;
}
