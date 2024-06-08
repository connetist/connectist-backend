package org.example.chatservice.chatRoom.domain;

import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ChatRoomtTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void 채팅방생성() {
        ChatRoom chatRoom = ChatRoom.builder().
                id("TestId").
                title("title").build();

        ChatRoom newChatRoom = chatService.saveMessage(chatRoom);
        assertThat(newChatRoom.getId()).isEqualTo("TestId");
        assertThat(newChatRoom.getTitle()).isEqualTo("title");
    }

}
