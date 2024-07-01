package com.example.alarmservice.util;

import org.springframework.stereotype.Component;

@Component
public class ClockHolderUnixTime implements ClockHolder {

    @Override
    public Long getNowUnixTime() {
        return System.currentTimeMillis() / 1000;
    }
}
