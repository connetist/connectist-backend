package com.example.userservice.util.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Object object) {
        super(object.toString() + " 값이 없습니다.");
    }
}
