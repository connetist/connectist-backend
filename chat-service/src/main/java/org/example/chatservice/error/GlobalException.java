package org.example.chatservice.error;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}