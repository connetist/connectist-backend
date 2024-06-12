package org.example.chatservice.utils;

import java.util.UUID;

public class UuidHolderImpl implements UuidHolder{
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
