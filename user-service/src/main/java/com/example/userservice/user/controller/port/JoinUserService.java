package com.example.userservice.user.controller.port;

import com.example.userservice.user.dto.request.UserJoinCertificationRequest;
import com.example.userservice.user.dto.request.UserJoinRequest;
import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserService {
    JoinUser join(UserJoinRequest userCreate);
    // certification
    JoinUser certification(UserJoinCertificationRequest joinUserCertification);

    JoinUser emailCertificationBeforeJoin(String email);

    boolean checkEmailBeforeCertification(String email);
}
