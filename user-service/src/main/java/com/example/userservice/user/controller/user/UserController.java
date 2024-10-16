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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import static com.example.userservice.user.dto.response.GlobalResponse.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    private final UserService userService;

    @GetMapping("/health")
    public ResponseEntity<GlobalResponse<String>> status() {
        String str = "User-service는" + serverPort + "에서 실행 중입니다";
        return of(SuccessCode.OK, str);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<User>> login(
            @RequestBody UserLoginRequest userLogin,
            ServerWebExchange exchange
    ) {
        new RequestCheck(userLogin).check();
        String serverPort = String.valueOf(webServerAppCtxt.getWebServer().getPort());
        log.info((serverPort));
        UserWithToken userWithToken = userService.login(userLogin);

        boolean serverPortCookieExists = exchange.getRequest().getCookies().containsKey("server-port");
        ResponseCookie accessCookie = ResponseCookie
                .from("access-token", userWithToken.getAccess())
                .httpOnly(true)
                .path("/")
                .secure(true)
                .build();
        ResponseCookie refreshCookie = ResponseCookie
                .from("refresh-token", userWithToken.getRefresh())
                .httpOnly(true)
                .path("/")
                .secure(true)
                .build();


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        if (!serverPortCookieExists){
            ResponseCookie serverCookie = ResponseCookie
                    .from("server-port",serverPort)
                    .httpOnly(true)
                    .path("/")
                    .secure(true)
                    .build();

            headers.add(HttpHeaders.SET_COOKIE, serverCookie.toString());
        }
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
    @PatchMapping
    public ResponseEntity<GlobalResponse<User>> updateUserController(
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        new RequestCheck(userUpdateRequest).check();

        User update = userService.update(userUpdateRequest);
        return of(SuccessCode.UPDATE_OK, update);
    }

    // user delete
    @DeleteMapping
    public ResponseEntity<GlobalResponse<User>> deleteUserController(
            @RequestBody UserDeleteRequest userDeleteRequest
    ) {
        new RequestCheck(userDeleteRequest).check();

        User user = userService.delete(userDeleteRequest);
        return of(SuccessCode.DELETE_OK, user);
    }

}

