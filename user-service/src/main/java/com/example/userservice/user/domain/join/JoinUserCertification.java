package com.example.userservice.user.domain.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JoinUserCertification {

    private final String email;
    private final String certificationCode;

    @Builder
    public JoinUserCertification(
            @JsonProperty("email") String email,
            @JsonProperty("certification-code") String certificationCode) {
        this.email = email;
        this.certificationCode = certificationCode;
    }
}
