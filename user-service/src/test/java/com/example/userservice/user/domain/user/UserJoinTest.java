package com.example.userservice.user.domain.user;

import com.example.userservice.user.domain.user.value.School;
import com.example.userservice.user.domain.user.value.UserStatus;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.util.certification.CertificationHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UserJoinTest {

    private UserJoinRequest userJoinRequest;
    private CertificationHolder certificationHolder;

    @BeforeEach
    void init() {
        userJoinRequest = new UserJoinRequest("email@test.com", 3);
        certificationHolder = new FakeCertificationHolder();
    }


    @DisplayName("UserJoin domain을 생성한다.")
    @Test
    void createUserJoin() {
        // when
        UserJoin userJoin = UserJoin.from(userJoinRequest, certificationHolder);
        // then
        Assertions.assertThat(userJoin.getSchool()).isEqualTo(School.GIST);
        Assertions.assertThat(userJoin.getEmail()).isEqualTo(userJoinRequest.getEmail());
        Assertions.assertThat(userJoin.getCertificationCode()).isEqualTo(certificationHolder.random());
        Assertions.assertThat(userJoin.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @DisplayName("UserJoin domain의 상태를 변경한다.")
    @Test
    void updateUserJoin() {
        // given
        UserJoin userJoin = UserJoin.from(userJoinRequest, certificationHolder);
        // when
        UserJoin userJoinModified = userJoin.updateStatus(UserStatus.ABLE);
        // then
        Assertions.assertThat(userJoinModified.getSchool()).isEqualTo(userJoin.getSchool());
        Assertions.assertThat(userJoinModified.getEmail()).isEqualTo(userJoin.getEmail());
        Assertions.assertThat(userJoinModified.getCertificationCode()).isEqualTo(userJoin.getCertificationCode());
        Assertions.assertThat(userJoinModified.getStatus()).isEqualTo(UserStatus.ABLE);
    }
    
    static class FakeCertificationHolder implements CertificationHolder {
        @Override
        public String random() {
            return "000000";
        }
    }

}