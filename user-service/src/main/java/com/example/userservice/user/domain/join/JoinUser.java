package com.example.userservice.user.domain.join;

import com.example.userservice.util.uuid.UuidHolder;
import com.example.userservice.user.domain.user.School;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinUser {

    private final String email;
    private final School school;
    private final String certificationCode;

    @Builder
    public JoinUser(
            String email,
            School school,
            String certificationCode
    ) {
        this.school = school;
        this.certificationCode = certificationCode;
        this.email = email;
    }

    public static JoinUser from(JoinUserCreate userCreate, UuidHolder uuidHolder) {
        return JoinUser.builder()
                .email(userCreate.getEmail())
                .school(userCreate.getSchool())
                .certificationCode(uuidHolder.random())
                .build();
    }

}
