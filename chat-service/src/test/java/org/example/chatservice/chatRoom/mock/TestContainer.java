package org.example.chatservice.chatRoom.mock;

import lombok.Builder;
import org.example.chatservice.chatRoom.controller.ChatController;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.example.chatservice.chatRoom.service.ChatRoomServiceImpl;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestContainer {
    public final ChatController chatController;
    public final ChatRoomRepository chatRoomRepository;
    public final ChatRoomService chatRoomService;

    private final UuidHolder uuidHolder;

    private final ClockHolder clockHolder;
    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.uuidHolder= uuidHolder;
        this.clockHolder = clockHolder;
        this.chatRoomRepository = new FakeChatRoomRepository();
        this.chatRoomService = ChatRoomServiceImpl.builder()
                .chatRoomRepository(chatRoomRepository)
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();
        this.chatController = ChatController.builder()
                .chatRoomService(chatRoomService)
                .build();



    }



}
