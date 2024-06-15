package com.example.userservice.user.controller;

import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.controller.request.UserLogin;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.token.UserWithToken;
import com.example.userservice.user.service.auth.JwtUtil;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
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
}

