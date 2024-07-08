package org.example.boardservice.utils.requestverify;

import lombok.AllArgsConstructor;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;

import java.lang.reflect.Field;

@AllArgsConstructor
public class RequestCheck {

    private final Object targetObject;

    public void checkString() {
        if (!(targetObject instanceof String)) {
            throw new GlobalException(ResultCode.INTERNAL_SERVER_ERROR);
        }
        if ( String.valueOf(targetObject).isEmpty()) {
            throw new GlobalException(ResultCode.INVALID_REQUEST);
        }
    }

    public void check() {
        Field[] fields = targetObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(targetObject) == null) {
                    throw new GlobalException(ResultCode.INVALID_REQUEST);
                }
            } catch (IllegalAccessException e) {
                throw new GlobalException(ResultCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}