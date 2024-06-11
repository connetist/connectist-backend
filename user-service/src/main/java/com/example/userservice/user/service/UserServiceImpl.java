package com.example.userservice.user.service;

import com.example.userservice.user.controller.port.UserService;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.service.port.UserRespository;
import com.example.userservice.util.clock.ClockHolder;
import com.example.userservice.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClockHolder clockHolder;
    private final UserRespository userRespository;


    @Override
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, clockHolder);
        user = userRespository.save(user);
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
