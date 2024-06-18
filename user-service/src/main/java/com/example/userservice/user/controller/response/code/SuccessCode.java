package com.example.userservice.user.controller.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SuccessCode {
    // 200 OK - 기본, 유저 조회/삭제/업데이트
    OK(HttpStatus.OK, "요청이 성공했습니다."),

    // 유저 R,U,D
    VALUE_OK(HttpStatus.OK, "조회에 성공했습니다."),
    DELETE_OK(HttpStatus.OK, "삭제에 성공했습니다."),
    UPDATE_OK(HttpStatus.OK, "유저 업데이트에 성공했습니다."),

    // 회원가입
    EMAIL_OK(HttpStatus.OK, "이메일 요청에 성공했습니다."),
    EMAIL_CREATED(HttpStatus.CREATED, "이메일을 통한 인증코드 생성에 성공했습니다."),
    EMAIL_VERIFIED(HttpStatus.OK, "이메일 인증에 성공했습니다."),
    USER_CREATED(HttpStatus.CREATED, "유저 생성에 성공했습니다."),

    // 로그인
    LOGIN_OK(HttpStatus.OK, "로그인에 성공했습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
