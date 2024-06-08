package org.example.chatservice.chatRoom.service;

import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.infrastructure.repository.ChatRepository;
import org.example.chatservice.service.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {


    private ChatServiceImpl chatService;

    private ChatRepository chatRepository;

    @BeforeEach
    void init(){
        chatRepository = mock(ChatRepository.class);
        chatService = ChatServiceImpl.builder()
                .chatRepository(chatRepository)
                .build();
    }
    @Test
    public void 채팅방생성() {
        ChatRoom chatRoom = ChatRoom.builder().
                id("TestId").
                title("title").build();

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.from(chatRoom);
        when(chatRepository.save(any(ChatRoomEntity.class))).thenReturn(chatRoomEntity);
//
        ChatRoom newChatRoom = chatService.saveMessage(chatRoom);
        assertThat(newChatRoom.getId()).isEqualTo("TestId");
        assertThat(newChatRoom.getTitle()).isEqualTo("title");
    }
}
