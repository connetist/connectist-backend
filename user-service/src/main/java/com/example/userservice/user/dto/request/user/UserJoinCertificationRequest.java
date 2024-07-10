package com.example.userservice.user.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserJoinCertificationRequest {

    private final String email;
    private final String certificationCode;

    @Builder
    public UserJoinCertificationRequest(
            @JsonProperty("email") String email,
            @JsonProperty("certification-code") String certificationCode) {
        this.email = email;
        this.certificationCode = certificationCode;
    }

    public static UserJoinCertificationRequest from(String email, String certificationCode) {
        return UserJoinCertificationRequest.builder()
                .email(email)
                .certificationCode(certificationCode)
                .build();
    }
}
