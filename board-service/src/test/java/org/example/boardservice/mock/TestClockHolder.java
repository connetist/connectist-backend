package org.example.boardservice.mock;

//import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.boardservice.utils.clock.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    @Override
    public long mills() {
        return millis;
    }
}