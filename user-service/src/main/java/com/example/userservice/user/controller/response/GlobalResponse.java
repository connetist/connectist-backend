package com.example.userservice.user.controller.response;

import com.example.userservice.user.controller.response.code.ErrorCode;
import com.example.userservice.user.controller.response.code.SuccessCode;
import com.example.userservice.user.error.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor @RequiredArgsConstructor
public class GlobalResponse<T> {

    private final int status;
    private final String message;
    private T data;

    public static ResponseEntity<GlobalResponse> of(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getStatus())
                .body(new GlobalResponse(successCode.getStatusCode(), successCode.getMessage()));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(SuccessCode successCode, T data) {
        return ResponseEntity.status(successCode.getStatus())
                .body(new GlobalResponse<T>(successCode.getStatusCode(), successCode.getMessage(), data));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(SuccessCode successCode, T data, HttpHeaders headers) {
        return ResponseEntity.status(successCode.getStatus())
                .headers(headers)
                .body(new GlobalResponse<T>(successCode.getStatusCode(), successCode.getMessage(), data));
    }

    public static ResponseEntity<GlobalResponse> of(GlobalException exception) {
        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(new GlobalResponse(exception.getErrorCode().getStatusCode(), exception.getErrorCode().getMessage()));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(ErrorCode errorCode, T data) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(new GlobalResponse<T>(errorCode.getStatusCode(), errorCode.getMessage(), data));
    }
}
