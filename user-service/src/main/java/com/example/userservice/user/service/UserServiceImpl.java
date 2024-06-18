package com.example.userservice.user.service;

import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.dto.request.UserDeleteRequest;
import com.example.userservice.user.dto.request.UserLoginRequest;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserUpdate;
import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.token.UserWithToken;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;
import com.example.userservice.user.service.port.JwtTokenService;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.clock.ClockHolder;
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
    private final JoinUserRepository joinUserRepository;

    // 회원가입
    @Override
    public User create(UserCreate userCreate) {

        JoinUser joinUser = joinUserService.emailCertificationBeforeJoin(userCreate.getEmail());

        try {
            User user = User.fromAfterCertification(userCreate, joinUser, clockHolder, idGenerator);
            User pwEncodedUser = user.encodePw(user, passwordEncoder.encode(user.getPassword()));

            User save = userRepository.save(pwEncodedUser);
            joinUserRepository.delete(save.getEmail());

            return save;
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.USER_CREATE_ERROR);
        }
    }

    @Override
    public UserWithToken login(UserLoginRequest userLogin) {

        User user = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(
                () -> new GlobalException(ErrorCode.LOGIN_ERROR)
        );

        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            throw new GlobalException(ErrorCode.WRONG_USER_PASSWORD);
        } else {
            String accessToken = jwtTokenService.accessToken(user);
            String refreshToken = jwtTokenService.refreshToken(user).getRefreshToken();
            return UserWithToken.from(user, accessToken, refreshToken);
        }
    }

    @Override
    public User update(UserUpdate userUpdate) {
        User user = findByEmail(userUpdate.getEmail());

        User updatedUser = User.fromWithUserUpdate(user, userUpdate);
        updatedUser = user.encodePw(user, passwordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser == null) {
            throw new GlobalException(ErrorCode.USER_UPDATE_ERROR);
        }
        return userRepository.update(updatedUser);
    }

    @Override
    public User delete(UserDeleteRequest userDeleteRequest) {

        User user = findByEmail(userDeleteRequest.getEmail());

        if (!passwordEncoder.matches(userDeleteRequest.getPassword(), user.getPassword())) {
            throw new GlobalException(ErrorCode.WRONG_USER_PASSWORD);
        }

        return userRepository.delete(user);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EMAIL));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_ID));
    }
}
