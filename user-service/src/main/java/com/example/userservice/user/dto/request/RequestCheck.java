package com.example.userservice.user.dto.request;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.util.exception.code.GlobalException;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;

@AllArgsConstructor
public class RequestCheck {

    private final Object targetObject;

    public void checkString() {
        if (!(targetObject instanceof String)) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if ( String.valueOf(targetObject).isEmpty()) {
            throw new GlobalException(ErrorCode.INVALID_INPUT);
        }
    }

    public void check() {
        Field[] fields = targetObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(targetObject) == null) {
                    throw new GlobalException(ErrorCode.INVALID_INPUT);
                }
            } catch (IllegalAccessException e) {
                throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
