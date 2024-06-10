package com.example.userservice.user.service;

import com.example.userservice.util.uuid.UuidHolder;
import com.example.userservice.user.controller.port.JoinUserService;
import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl implements JoinUserService {

    private final UuidHolder uuidHolder;
    private final JoinUserRepository joinUserRepository;
    private final CertificationService certificationService;

    @Override
    public JoinUser create(UserCreate userCreate) {
        JoinUser joinUser = JoinUser.from(userCreate, uuidHolder);
        joinUser = joinUserRepository.save(joinUser);
        certificationService.send(joinUser.getEmail(), joinUser.getCertificationCode());
        return joinUser;
    }
}
