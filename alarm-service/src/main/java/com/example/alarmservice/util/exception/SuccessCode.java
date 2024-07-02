package com.example.alarmservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // token 저장 성공
    TOKEN_SAVE_SUCCESS(HttpStatus.CREATED, "토큰 저장에 성공했습니다."),
    // token 찾기 성공
    TOKEN_FIND_SUCCESS(HttpStatus.OK, "토큰 찾기에 성공했습니다."),
    // 알림 전송 완료
    ALARM_SEND_SUCCESS(HttpStatus.OK, "알림 전송을 완료하였습니다."),
    // 성공
    SUCCESS(HttpStatus.OK, "요청에 대한 작업 수행을 성공하였습니다."),
    // token update 완료
    TOKEN_UPDATE_SUCCESS(HttpStatus.OK, "토큰 갱신에 성공하였습니다.");


    private final HttpStatus status;
    private final String message;
}
