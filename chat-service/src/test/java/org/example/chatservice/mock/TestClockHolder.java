package org.example.chatservice.mock;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.utils.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    @Override
    public long mills() {
        return millis;
    }


}