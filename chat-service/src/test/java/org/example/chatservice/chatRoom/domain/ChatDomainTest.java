package org.example.chatservice.chatRoom.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChatDomainTest {

    @Test
    public void 채팅방_도메인_생성(){
        ChatRoom chatRoom = ChatRoom.builder().
                id("TestId").
                title("title").
                build();

        assertThat(chatRoom.getId()).isEqualTo("TestId");
        assertThat(chatRoom.getTitle()).isEqualTo("title");
    }


}
