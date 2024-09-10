package com.example.userservice.user.domain.user;

import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.user.domain.user.value.School;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserJoin {

    private final String email;
    private final School school;
    private final String certificationCode;
    private final UserStatus status;

    @Builder
    public UserJoin(
            String email,
            School school,
            String certificationCode,
            UserStatus status
    ) {
        this.school = school;
        this.certificationCode = certificationCode;
        this.email = email;
        this.status = status;
    }

    public static UserJoin from(UserJoinRequest userCreate, CertificationHolder certificationHolder) {
        return UserJoin.builder()
                .email(userCreate.getEmail())
                .school(userCreate.getSchool())
                .status(UserStatus.PENDING)
                .certificationCode(certificationHolder.random())
                .build();
    }

    public UserJoin updateStatus(UserStatus status) {
        return UserJoin.builder()
                .email(email)
                .school(school)
                .status(status)
                .certificationCode(certificationCode)
                .build();
    }

}
