package com.example.userservice.user.dto.response;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.util.exception.code.SuccessCode;
import com.example.userservice.util.exception.code.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor @RequiredArgsConstructor
public class GlobalResponse<T> {

    private final boolean success;
    private final String message;
    private T data;

    public static ResponseEntity<GlobalResponse> of(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getStatus())
                .body(new GlobalResponse(true, successCode.getMessage()));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(SuccessCode successCode, T data) {
        return ResponseEntity.status(successCode.getStatus())
                .body(new GlobalResponse<T>(true, successCode.getMessage(), data));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(SuccessCode successCode, T data, HttpHeaders headers) {
        return ResponseEntity.status(successCode.getStatus())
                .headers(headers)
                .body(new GlobalResponse<T>(true, successCode.getMessage(), data));
    }

    public static ResponseEntity<GlobalResponse> of(GlobalException exception) {
        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(new GlobalResponse(false, exception.getErrorCode().getMessage()));
    }

    public static <T> ResponseEntity<GlobalResponse<T>> of(ErrorCode errorCode, T data) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(new GlobalResponse<T>(false, errorCode.getMessage(), data));
    }
}
