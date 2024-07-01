package org.example.boardservice.lab.service;

import lombok.Builder;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.board.service.LabService;
import org.example.boardservice.board.service.LabServiceImpl;
import org.example.boardservice.mock.FakeLabRepository;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

public class LabServiceTest {
    private LabServiceImpl labService;
    private LabRepository labRepository;

    @BeforeEach
    void init(){
        labRepository = new FakeLabRepository();
        UuidHolder uuidHolder = new TestUuidHolder("testId");
        ClockHolder clockHolder = new TestClockHolder(1000);

        labService = LabServiceImpl.builder()
                .labRepository(labRepository)
                .build();
    }
}
