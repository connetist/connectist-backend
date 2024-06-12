package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
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


    private ChatRoomServiceImpl chatRoomService;

    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    void init(){
        chatRoomRepository = mock(ChatRoomRepository.class);
        chatRoomService = ChatRoomServiceImpl.builder()
                .chatRoomRepository(chatRoomRepository)
                .build();
    }
    @Test
    public void 채팅방생성() {
//        ChatRoom chatRoom = ChatRoom.builder().
//                id("TestId").
//                title("title").build();
//
//        ChatRoomEntity chatRoomEntity = ChatRoomEntity.from(chatRoom);
//        when(chatRoomRepository.save(any(ChatRoomEntity.class))).thenReturn(chatRoomEntity);
////
//        ChatRoom newChatRoom = chatRoomService.(chatRoom);
//        assertThat(newChatRoom.getId()).isEqualTo("TestId");
//        assertThat(newChatRoom.getTitle()).isEqualTo("title");
    }
}
