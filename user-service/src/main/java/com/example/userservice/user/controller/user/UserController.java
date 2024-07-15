package com.example.userservice.user.controller.user;

import com.example.userservice.user.service.user.UserService;
import com.example.userservice.user.dto.request.RequestCheck;
import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.util.exception.code.SuccessCode;
import com.example.userservice.user.dto.request.user.UserDeleteRequest;
import com.example.userservice.user.dto.request.user.UserLoginRequest;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.dto.response.token.UserWithToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static com.example.userservice.user.dto.response.GlobalResponse.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Value("${server.port}")
    private String serverPort;

    private final UserService userService;

    @GetMapping("/health")
    public ResponseEntity<GlobalResponse<String>> status() {
        String str = "Chat-service는" + serverPort + "에서 실행 중입니다";
        return of(SuccessCode.OK, str);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<User>> login(
            @RequestBody UserLoginRequest userLogin
    ) {
        new RequestCheck(userLogin).check();

        UserWithToken userWithToken = userService.login(userLogin);
        ResponseCookie accessCookie = ResponseCookie
                .from("access-token", userWithToken.getAccess())
                .httpOnly(true)
                .secure(true)
                .build();
        ResponseCookie refreshCookie = ResponseCookie
                .from("refresh-token", userWithToken.getRefresh())
                .httpOnly(true)
                .secure(true)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        return of(SuccessCode.LOGIN_OK, userWithToken.getUser(), headers);

    }


    // user id로 찾기
    @GetMapping("/id/{id}")
    public ResponseEntity<GlobalResponse<User>> findByIdController(
            @PathVariable("id") String id
    ) {
        new RequestCheck(id).checkString();

        return of(SuccessCode.VALUE_OK, userService.findById(id));
    }

    // user email로 찾기
    @GetMapping("/email/{email}")
    public ResponseEntity<GlobalResponse<User>> findByEmailController(
            @PathVariable ("email") String email
    ) {
        new RequestCheck(email).checkString();

        return of(SuccessCode.VALUE_OK, userService.findByEmail(email));
    }

    // user update
    @PatchMapping("/{email}")
    public ResponseEntity<GlobalResponse<User>> updateUserController(
            @PathVariable("email") String email,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        new RequestCheck(email).checkString();
        new RequestCheck(userUpdateRequest).check();

        User update = userService.update(userUpdateRequest);
        return of(SuccessCode.UPDATE_OK, update);
    }

    // user delete
    @DeleteMapping("/{email}")
    public ResponseEntity<GlobalResponse<User>> deleteUserController(
            @PathVariable("email") String email,
            @RequestBody UserDeleteRequest userDeleteRequest
    ) {
        new RequestCheck(email).checkString();
        new RequestCheck(userDeleteRequest).check();

        User user = userService.delete(userDeleteRequest);
        return of(SuccessCode.DELETE_OK, user);
    }

}

