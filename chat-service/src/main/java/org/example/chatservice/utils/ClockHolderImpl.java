package org.example.chatservice.utils;

import java.time.Instant;

public class ClockHolderImpl implements ClockHolder{
    public long mills(){
        return Instant.now().getEpochSecond();
    }
}
