package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.dto.request.RequestCheck;
import com.example.userservice.user.dto.response.GlobalResponse;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.util.exception.code.SuccessCode;
import com.example.userservice.user.dto.request.UserDeleteRequest;
import com.example.userservice.user.dto.request.UserLoginRequest;
import com.example.userservice.user.dto.request.UserUpdateRequest;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserUpdate;
import com.example.userservice.user.domain.token.UserWithToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;

    // login
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<User>> login(
            @RequestBody UserLoginRequest userLogin
    ) {
        RequestCheck requestCheck = new RequestCheck(userLogin);
        requestCheck.check();

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
        RequestCheck requestCheck = new RequestCheck(id);
        requestCheck.checkString();

        return of(SuccessCode.VALUE_OK, userService.findById(id));
    }

    // user email로 찾기
    @GetMapping("/email/{email}")
    public ResponseEntity<GlobalResponse<User>> findByEmailController(
            @PathVariable ("email") String email
    ) {
        RequestCheck requestCheck = new RequestCheck(email);
        requestCheck.checkString();

        return of(SuccessCode.VALUE_OK, userService.findByEmail(email));
    }

    // user update
    @PatchMapping("/{email}")
    public ResponseEntity<GlobalResponse<UserUpdate>> updateUserController(
            @PathVariable("email") String email,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        RequestCheck emailCheck = new RequestCheck(email);
        emailCheck.checkString();
        RequestCheck requestCheck = new RequestCheck(userUpdateRequest);
        requestCheck.check();

        UserUpdate userUpdate = UserUpdate.fromWithRequest(userUpdateRequest);
        return of(SuccessCode.UPDATE_OK, userUpdate);
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

