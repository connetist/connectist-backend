package com.example.userservice.user.dto.request.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDeleteRequest {
    private String email;
    private String password;

}
