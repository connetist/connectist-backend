package com.example.userservice.user.service.auth;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.infrastructure.UserAuthEntity;
import com.example.userservice.user.service.port.UserRepository;
import com.example.userservice.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new NotFoundException(username)
        );
        log.info(user.getEmail() + " user detail service 1");
        UserAuthEntity userAuthEntity = UserAuthEntity.from(user);
        log.info(userAuthEntity.getEmail() + " user detail service 2");
        if (userAuthEntity != null) {
            return new CustomerUserDetail(userAuthEntity);
        }
        throw new UsernameNotFoundException(username);
    }
}
