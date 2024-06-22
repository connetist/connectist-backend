package com.example.userservice.user.dto.request;

import com.example.userservice.util.exception.code.GlobalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class RequestCheckTest {

    @Test
    @DisplayName("값이 유효하지 않은지 확인한다.")
    void 값이_유효하지_않은지_확인() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .pw("password")
                .degree(1)
                .sex(1)
                .major(1)
                .nickname("test")
                .build();

        RequestCheck rc = new RequestCheck(userCreateRequest);
        Assertions.assertThrows(GlobalException.class, rc::check);
    }

    @Test
    @DisplayName("String 값 null일때 예외를 띄운다")
    void 문자열의_값이_null이면_예외() {
        String s = null;
        RequestCheck rc = new RequestCheck(s);
        Assertions.assertThrows(GlobalException.class, rc::checkString);
    }

    @Test
    @DisplayName("String 값 없을 때 예외를 띄운다")
    void 문자열의_값이_없으면_예외() {
        String s = "";
        RequestCheck rc = new RequestCheck(s);
        Assertions.assertThrows(GlobalException.class, rc::checkString);
    }

    @Test
    @DisplayName("잘못된 메서드를 호출한다.")
    void 잘못된_메서드를_호출하면_예외발생() {
        Integer i = 3;
        RequestCheck rc = new RequestCheck(i);
        Assertions.assertThrows(GlobalException.class, rc::checkString);
    }

    @Test
    @DisplayName("primitive type에 대해서 작동테스트")
    void primitiveTest() {
        Example example = new Example(1);
        RequestCheck requestCheck = new RequestCheck(example);
        requestCheck.check();
    }
    static class Example {
        int anInt;
        public Example(int anInt) {
            this.anInt = anInt;
        }
    }
}