package com.example.userservice.util.clock;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public Long getNowUnixTime() {
        return System.currentTimeMillis() / 1000;
    }
}
