package org.example.chatservice.chatMessage.domain;

import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.mock.TestClockHolder;
import org.example.chatservice.mock.TestUuidHolder;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class ChatMessageDomainTest {

    private UuidHolder uuidHolder = new TestUuidHolder("testUid");
    private ClockHolder clockHolder = new TestClockHolder(1000);

    @Test
    public void 채팅_메세지_도메인_빌더(){
        ChatMessage chatMessage = ChatMessage.builder()
                .id("testId")
                .userId("testUserId")
                .roomId("testRoomId")
                .content("testContent")
                .createdAt(1000)
                .build();

        assertThat(chatMessage.getId()).isEqualTo("testId");
        assertThat(chatMessage.getUserId()).isEqualTo("testUserId");
        assertThat(chatMessage.getRoomId()).isEqualTo("testRoomId");
        assertThat(chatMessage.getContent()).isEqualTo("testContent");
        assertThat(chatMessage.getCreatedAt()).isEqualTo(1000);
    }

    @Test
    public void 채팅_메세지_생성(){
        CreateChatMessageRequest rq = CreateChatMessageRequest.builder()
                .roomId("testRoomId")
                .content("testContent")
                .userId("testUserId")
                .build();

        ChatMessage chatMessage = ChatMessage.createChatMessage(rq,uuidHolder,clockHolder);

        assertThat(chatMessage.getId()).isEqualTo("testUid");
        assertThat(chatMessage.getUserId()).isEqualTo("testUserId");
        assertThat(chatMessage.getRoomId()).isEqualTo("testRoomId");
        assertThat(chatMessage.getContent()).isEqualTo("testContent");
        assertThat(chatMessage.getCreatedAt()).isEqualTo(1000);

    }
}
