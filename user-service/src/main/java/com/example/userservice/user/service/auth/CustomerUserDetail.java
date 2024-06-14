package com.example.userservice.user.service.auth;

import com.example.userservice.user.infrastructure.UserAuthEntity;
import com.example.userservice.user.infrastructure.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

 @RequiredArgsConstructor
public class CustomerUserDetail implements UserDetails {

    private final UserAuthEntity userAuthEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userAuthEntity.getUserStatus().toString();
            }
        });

        return collection;
    }


    @Override
    public String getPassword() {
        return userAuthEntity.getPw();
    }

    @Override
    public String getUsername() {
        return userAuthEntity.getId();
    }
}
