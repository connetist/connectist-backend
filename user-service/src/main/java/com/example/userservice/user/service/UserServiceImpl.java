package com.example.userservice.user.service;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.service.port.UserRespository;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.exception.NotFoundException;
import com.example.userservice.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClockHolder clockHolder;
    private final UserRespository userRespository;
    private final JoinUserService joinUserService;
    private final PasswordEncoder passwordEncoder;
    private final IdGenerator idGenerator;

    // 회원가입
    @Override
    public User create(UserCreate userCreate) {
        // eamil 인증 받았는지 확인
        JoinUser joinUser = joinUserService.emailCertificationBeforeJoin(userCreate.getEmail());
        // 비번 암호화
        userCreate = userCreate.updatePwAfterEncode(userCreate, passwordEncoder.encode(userCreate.getPw()));
        // 유저 만들고
        User user = User.from(userCreate, clockHolder, idGenerator);
        // 저장하고
        user = userRespository.save(user);
        // 반환
        return user;
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
        return userRespository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(email));
    }

    @Override
    public User findById(String id) {
        return userRespository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
