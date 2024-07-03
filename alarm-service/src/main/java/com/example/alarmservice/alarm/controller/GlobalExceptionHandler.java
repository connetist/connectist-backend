package com.example.alarmservice.alarm.controller;

import com.example.alarmservice.alarm.dto.response.GlobalResponse;
import com.example.alarmservice.util.exception.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalResponse> handleGlobalException(GlobalException exception) {
        return GlobalResponse.of(exception);
    }
}
