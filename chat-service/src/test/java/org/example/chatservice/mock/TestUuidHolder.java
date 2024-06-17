package org.example.chatservice.mock;


import lombok.RequiredArgsConstructor;
import org.example.chatservice.utils.UuidHolder;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    @Override
    public String random() {
        return uuid;
    }
}