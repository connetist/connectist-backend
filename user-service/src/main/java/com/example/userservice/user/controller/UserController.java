package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.dto.request.UserDeleteRequest;
import com.example.userservice.user.dto.request.UserLogin;
import com.example.userservice.user.dto.request.UserUpdateRequest;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserUpdate;
import com.example.userservice.user.domain.token.UserWithToken;

import com.example.userservice.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // login
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody UserLogin userLogin
    ) {
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

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(userWithToken.getUser());
    }


    // user id로 찾기
    @GetMapping("/id/{id}")
    public User findByIdController(
            @PathVariable("id") String id
    ) {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    // user email로 찾기
    @GetMapping("/email/{email}")
    public User findByEmailController(
            @PathVariable ("email") String email
    ) {
        try {
            return userService.findByEmail(email);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    // user update
    @PatchMapping("/{email}")
    public User updateUserController(
            @PathVariable("email") String email,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) throws Exception {
        if(!email.equals(userUpdateRequest.getEmail())){
            throw new Exception("email과 request가 다릅니다.");
        }
        UserUpdate userUpdate = UserUpdate.fromWithRequest(userUpdateRequest);
        return userService.update(userUpdate);
    }

    // user delete
    @DeleteMapping("/{email}")
    public ResponseEntity<User> deleteUserController(
            @PathVariable("email") String email,
            @RequestBody UserDeleteRequest userDeleteRequest
    ) throws Exception {
        if(!email.equals(userDeleteRequest.getEmail())){
            throw new Exception("email과 request가 다릅니다.");
        }
        User user = userService.delete(userDeleteRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}

