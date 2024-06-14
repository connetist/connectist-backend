package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .id("testId1")
                .title("testTitle1")
                .chatMembers(chatMemberList)
                .deparature("testDeparture1")
                .destination("testDestination1")
                .timeTaken(100)
                .startTime(100)
                .fee(1000)
                .createdAt(10000)
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .id("testId2")
                .title("testTitle2")
                .chatMembers(chatMemberList)
                .deparature("testDeparture2")
                .destination("testDestination2")
                .timeTaken(200)
                .startTime(200)
                .fee(2000)
                .createdAt(20000)
                .build();

        ChatRoomEntity chatRoomEntity1 = ChatRoomEntity.from(chatRoom1);
        ChatRoomEntity chatRoomEntity2 = ChatRoomEntity.from(chatRoom2);

        List<ChatRoomEntity> chatRoomEntities = new ArrayList<>();
        chatRoomEntities.add(chatRoomEntity1);
        chatRoomEntities.add(chatRoomEntity2);
        when(chatRoomRepository.findById("testId")).thenReturn(Optional.ofNullable(chatRoomEntity1));
        when(chatRoomRepository.findAll()).thenReturn(chatRoomEntities);
    }
//    @Test
//    public void 채팅방생성() {
//
//        String id = "testId";
//
//        ChatRoom chatRoom = chatRoomService.getChatRoom(id);
//
//        assertThat(chatRoom.getId()).isEqualTo(id);
//
//    }
//
//    @Test
//    public


}
