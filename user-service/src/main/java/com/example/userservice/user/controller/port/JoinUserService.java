package com.example.userservice.user.controller.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.join.JoinUserCertification;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserService {
    JoinUser join(JoinUserCreate UserCreate);

    // certification
    JoinUser certification(JoinUserCertification joinUserCertification);

    // 회원가입
    User joinWithInfo(User user);


}
