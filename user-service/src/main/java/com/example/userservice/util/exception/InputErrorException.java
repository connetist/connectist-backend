package com.example.userservice.util.exception;

public class InputErrorException extends RuntimeException{
    public InputErrorException(Integer input) {
        super(input + " 값이 유효하지 않습니다");
    }
}
