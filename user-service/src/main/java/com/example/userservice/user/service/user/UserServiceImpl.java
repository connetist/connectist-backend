package com.example.userservice.user.service.user;

import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.user.service.token.JwtTokenService;
import com.example.userservice.user.service.user.join.JoinUserService;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.dto.request.user.UserDeleteRequest;
import com.example.userservice.user.dto.request.user.UserLoginRequest;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.dto.response.token.UserWithToken;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;
import com.example.userservice.user.infrastructure.user.UserRepository;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.id.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Builder
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
    public User create(UserCreateRequest userCreateRequest) {

        UserJoin userJoin = joinUserService.emailCertificationBeforeJoin(userCreateRequest.getEmail());

        try {
            User user = User.create(userCreateRequest, userJoin, clockHolder, idGenerator);
            if(userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new GlobalException(ErrorCode.USER_DUPLICATE_ERROR);
            }

            User pwEncodedUser = User.encodePw(user, passwordEncoder.encode(user.getPassword()));

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
    public User update(UserUpdateRequest userUpdateRequest) {
        User user = findByEmail(userUpdateRequest.getEmail());

        User updatedUser = User.update(user, userUpdateRequest);
        updatedUser = User.encodePw(updatedUser, passwordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser == null) {
            throw new GlobalException(ErrorCode.USER_UPDATE_ERROR);
        }
        return userRepository.update(updatedUser);
    }

    @Transactional
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
