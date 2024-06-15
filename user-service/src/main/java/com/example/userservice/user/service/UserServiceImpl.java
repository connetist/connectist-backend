package com.example.userservice.user.service;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.controller.request.UserLogin;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.token.UserWithToken;
import com.example.userservice.user.service.auth.JwtUtil;
import com.example.userservice.user.service.port.JwtTokenService;
import com.example.userservice.user.service.port.TokenRepository;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.exception.NotFoundException;
import com.example.userservice.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClockHolder clockHolder;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final JoinUserService joinUserService;
    private final PasswordEncoder passwordEncoder;
    private final IdGenerator idGenerator;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Override
    public User create(UserCreate userCreate) {
        // eamil 인증 받았는지 확인
        JoinUser joinUser = joinUserService.emailCertificationBeforeJoin(userCreate.getEmail());
        // 비번 암호화
        userCreate = userCreate.updatePwAfterEncode(userCreate, passwordEncoder.encode(userCreate.getPw()));
        // 유저 만들고
        User user = User.fromAfterCertification(userCreate, joinUser, clockHolder, idGenerator);
        // 저장하고
        user = userRepository.save(user);
        // 반환
        return user;
    }

    @Override
    public UserWithToken login(UserLogin userLogin) {
        log.info(userLogin.getEmail());
        User user = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(
                () -> new NotFoundException("존재하지 않습니다.")
        );
        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPw())) {
            throw new NotFoundException("비번 오류입니다.");
        }
        String accessToken = jwtTokenService.accessToken(user);
        String refreshToken = jwtTokenService.refreshToken(user).getRefreshToken();
        return UserWithToken.from(user, accessToken, refreshToken);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(String id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(email));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
