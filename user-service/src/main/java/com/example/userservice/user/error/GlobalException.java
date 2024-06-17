package com.example.userservice.user.error;

import com.example.userservice.user.controller.response.code.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
