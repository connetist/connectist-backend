package org.example.boardservice.mock;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.utils.uuid.UuidHolder;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    @Override
    public String random() {
        return uuid;
    }
}