package com.example.userservice.user.controller;

import com.example.userservice.user.controller.response.GlobalResponse;
import com.example.userservice.user.controller.response.code.ErrorCode;
import com.example.userservice.user.error.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalResponse> handleGlobalException(GlobalException ex) {
        return GlobalResponse.of(ex);
    }
}
