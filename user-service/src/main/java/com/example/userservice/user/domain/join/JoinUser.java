package com.example.userservice.user.domain.join;

import com.example.userservice.user.domain.user.UserStatus;
import com.example.userservice.user.dto.request.UserJoinRequest;
import com.example.userservice.util.certification.CertificationHolder;
import com.example.userservice.user.domain.user.School;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JoinUser {

    private final String email;
    private final School school;
    private final String certificationCode;
    private final UserStatus status;

    @Builder
    public JoinUser(
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

    public static JoinUser from(UserJoinRequest userCreate, CertificationHolder certificationHolder) {
        return JoinUser.builder()
                .email(userCreate.getEmail())
                .school(userCreate.getSchool())
                .status(UserStatus.PENDING)
                .certificationCode(certificationHolder.random())
                .build();
    }

    public JoinUser updateStatus(JoinUser joinUser, UserStatus status) {
        return JoinUser.builder()
                .email(joinUser.getEmail())
                .school(joinUser.getSchool())
                .status(status)
                .certificationCode(joinUser.getCertificationCode())
                .build();
    }

}
