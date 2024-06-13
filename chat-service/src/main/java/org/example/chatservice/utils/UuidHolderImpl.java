package org.example.chatservice.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidHolderImpl implements UuidHolder{
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
