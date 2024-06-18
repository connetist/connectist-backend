package com.example.userservice.user.controller.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 500 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    // 회원 C, R, U, D 실패
    USER_CREATE_ERROR(HttpStatus.FORBIDDEN, "유저 생성에 실패하였습니다"),
    USER_DUPLICATE_ERROR(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),
    USER_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "확인되지 않은 이메일입니다."),
    USER_EMAIL_CHECK_FAIL(HttpStatus.FORBIDDEN, "이메일과 학교 정보가 유효하지 않습니다."),
    WRONG_USER_PASSWORD(HttpStatus.FORBIDDEN, "아이디나 비밀번호가 잘못되었습니다."),

    USER_NOT_FOUND_ID(HttpStatus.NOT_FOUND, "해당 id의 유저가 없습니다."),
    USER_NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "해당 eamil의 유저가 없습니다."),
    USER_UPDATE_ERROR(HttpStatus.FORBIDDEN, "유저 업데이트에 실패하였습니다."),
    USER_DELETE_ERROR(HttpStatus.FORBIDDEN, "유저 삭제에 실패했습니다."),
    // 권한 문제
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "작업을 수행 할 권한이 없습니다."),

    // 로그인 실패
    LOGIN_ERROR(HttpStatus.FORBIDDEN, "로그인에 실패하였습니다."),

    // request error
    INVALID_REQUEST(HttpStatus.FORBIDDEN, "잘못된 입력입니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
