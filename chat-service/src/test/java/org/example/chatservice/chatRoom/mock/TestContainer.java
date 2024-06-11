package org.example.chatservice.chatRoom.mock;

import lombok.Builder;
import org.example.chatservice.controller.ChatController;
import org.example.chatservice.infrastructure.repository.ChatRepository;
import org.example.chatservice.service.ChatService;
import org.example.chatservice.service.ChatServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestContainer {
    public final ChatController chatController;
    public final ChatRepository chatRepository;
    public final ChatService chatService;
    @Builder
    public TestContainer() {
        this.chatRepository = mock(ChatRepository.class);
        this.chatService = ChatServiceImpl.builder()
                .chatRepository(chatRepository)
                .build();
        this.chatController = ChatController.builder()
                .chatService(chatService)
                .build();

    }



}
