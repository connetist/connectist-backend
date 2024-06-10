package com.example.userservice.user.domain.join;

import com.example.userservice.util.uuid.UuidHolder;
import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.domain.UserSchool;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinUser {

    private final String email;
    private final UserSchool userSchool;
    private final String certificationCode;

    @Builder
    public JoinUser(
            String email,
            UserSchool userSchool,
            String certificationCode
    ) {
        this.userSchool = userSchool;
        this.certificationCode = certificationCode;
        this.email = email;
    }

    public static JoinUser from(UserCreate userCreate, UuidHolder uuidHolder) {
        return JoinUser.builder()
                .email(userCreate.getEmail())
                .userSchool(userCreate.getSchool())
                .certificationCode(uuidHolder.random())
                .build();
    }

}
