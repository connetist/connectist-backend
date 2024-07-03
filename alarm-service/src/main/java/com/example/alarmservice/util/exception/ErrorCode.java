package com.example.alarmservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 500 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ALARM_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류로 인하여 알림 발송에 실패했습니다."),

    // id 문제
    TOKEN_NOTFOUND(HttpStatus.NOT_FOUND, "id에 해당하는 token이 없습니다."),
    // token 문제
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "token이 잘못되었습니다."),

    // 권한 문제
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "작업을 수행 할 권한이 없습니다."),

    // request error
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다"),
    INVALID_REQUEST(HttpStatus.FORBIDDEN, "잘못된 접근 권한입니다.");

    private final HttpStatus status;
    private final String message;
}
