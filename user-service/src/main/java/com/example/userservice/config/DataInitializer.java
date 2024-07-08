package com.example.userservice.config;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.user.*;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.clock.ClockHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder, ClockHolder clockHolder) {
        User user = User.builder()
                .id("admin")
                .email("admin@email.com")
                .pw(passwordEncoder.encode("admin"))
                .sex(UserSex.MALE)
                .school(School.KAIST)
                .major(UserMajor.AI)
                .nickname("admin")
                .degree(UserDegree.Doctor)
                .createdAt(clockHolder.getNowUnixTime())
                .status(UserStatus.ADMIN)
                .build();

        return args -> userRepository.save(user);
    }

}
