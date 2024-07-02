package org.example.boardservice.error;

import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<RestResponse<?>> handleGlobalException(GlobalException ex) {
        RestResponse<?> response = RestResponse.error(ex.getResultCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getResultCode().getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<?>> handleException(Exception ex) {
        log.error("Unhandled exception caught", ex);
        RestResponse<?> response = RestResponse.error(ResultCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}