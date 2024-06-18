package com.example.userservice.util.exception.code;

import com.example.userservice.util.exception.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
