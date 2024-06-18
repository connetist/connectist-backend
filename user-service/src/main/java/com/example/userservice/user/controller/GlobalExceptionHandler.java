package com.example.userservice.user.controller;

import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.util.exception.code.GlobalException;
import lombok.extern.slf4j.Slf4j;
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
