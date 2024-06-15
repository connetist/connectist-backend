package com.example.userservice.user.infrastructure.join;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.user.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthEntity {

    private String id;
    private String pw;
    private String email;
    private UserStatus userStatus;

    public static UserAuthEntity from(User user) {
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(user.getId());
        userAuthEntity.setEmail(user.getEmail());
        userAuthEntity.setPw(user.getPw());
        userAuthEntity.setUserStatus(user.getStatus());
        return userAuthEntity;
    }

    public User toModel() {
        return User.builder()
                .email(email)
                .pw(pw)
                .id(id)
                .status(userStatus)
                .build();
    }
}
