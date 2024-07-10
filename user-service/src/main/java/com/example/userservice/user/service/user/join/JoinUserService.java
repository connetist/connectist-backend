package com.example.userservice.user.service.user.join;

import com.example.userservice.user.dto.request.user.UserJoinCertificationRequest;
import com.example.userservice.user.dto.request.user.UserJoinRequest;
import com.example.userservice.user.domain.user.UserJoin;

public interface JoinUserService {
    UserJoin join(UserJoinRequest userCreate);
    // certification
    UserJoin certification(UserJoinCertificationRequest joinUserCertification);

    UserJoin emailCertificationBeforeJoin(String email);

    boolean checkEmailBeforeCertification(String email);
}
