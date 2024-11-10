package org.example.chatservice.mock;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.utils.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private long currentTime;

    public TestClockHolder(long startTime) {
        this.currentTime = startTime;
    }

    @Override
    public long mills() {
        return currentTime++;
    }



}