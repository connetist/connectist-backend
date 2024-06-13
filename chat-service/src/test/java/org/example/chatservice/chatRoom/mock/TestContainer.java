package org.example.chatservice.chatRoom.mock;

import lombok.Builder;
import org.example.chatservice.chatRoom.controller.ChatController;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.example.chatservice.chatRoom.service.ChatRoomServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestContainer {
    public final ChatController chatController;
    public final ChatRoomRepository chatRoomRepository;
    public final ChatRoomService chatRoomService;
    @Builder
    public TestContainer() {
        this.chatRoomRepository = mock(ChatRoomRepository.class);
        this.chatRoomService = ChatRoomServiceImpl.builder()
                .chatRoomRepository(chatRoomRepository)
                .build();
        this.chatController = ChatController.builder()
                .chatRoomService(chatRoomService)
                .build();

    }



}
