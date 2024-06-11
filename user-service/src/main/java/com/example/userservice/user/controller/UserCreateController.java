package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.response.UserJoinResponse;
import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final JoinUserService joinUserService;

    // user Create
    @PostMapping
    public ResponseEntity<UserJoinResponse> create(@RequestBody JoinUserCreate userCreate) {
        log.info(userCreate.toString());
        JoinUser joinUser = joinUserService.join(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserJoinResponse.from(joinUser));
    }

    // verify certification code after user create

    // 회원 가입


}
