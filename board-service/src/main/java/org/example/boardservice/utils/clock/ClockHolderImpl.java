package org.example.boardservice.utils.clock;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ClockHolderImpl implements ClockHolder{
    public long mills(){
        return Instant.now().getEpochSecond();
    }
}
