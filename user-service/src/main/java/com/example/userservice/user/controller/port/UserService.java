package com.example.userservice.user.controller.port;

import com.example.userservice.user.dto.request.UserDeleteRequest;
import com.example.userservice.user.dto.request.UserLoginRequest;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserUpdate;
import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.token.UserWithToken;

public interface UserService {

    // 회원가입
    User create(UserCreate userCreate);

    // 로그인
    UserWithToken login(UserLoginRequest userLogin);

    // 사용자 정보 업데이트
    User update(UserUpdate userUpdate);

    /**
     * 사용자 삭제하기
     * @param userDeleteRequest
     * @return User(삭제한 객체)
     */
    User delete(UserDeleteRequest userDeleteRequest);

    /**
     * 이메일로부터 사용자 찾기
     * @param email
     * @return User
     */
    User findByEmail(String email);

    /**
     * 아이디로부터 사용자 찾기
     * @param id
     * @return User
     */
    User findById(String id);
}
