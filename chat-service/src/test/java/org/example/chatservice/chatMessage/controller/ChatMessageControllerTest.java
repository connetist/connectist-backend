//package org.example.chatservice.chatMessage.controller;
//
//import org.example.chatservice.chatMessage.domain.ChatMessage;
//import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
//import org.example.chatservice.chatRoom.dto.Response.RestResponse;
//import org.example.chatservice.mock.TestClockHolder;
//import org.example.chatservice.mock.TestContainer;
//import org.example.chatservice.mock.TestUuidHolder;
//import org.example.chatservice.utils.ClockHolder;
//import org.example.chatservice.utils.UuidHolder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public class ChatMessageControllerTest {
//    private TestContainer testContainer;
//
//    @BeforeEach
//    void init(){
//        UuidHolder uuidHolder = new TestUuidHolder("testId");
//        ClockHolder clockHolder = new TestClockHolder(1000);
//
//        this.testContainer = TestContainer.builder()
//                .uuidHolder(uuidHolder)
//                .clockHolder(clockHolder)
//                .build();
//
//        ChatMessage chatMessage = ChatMessage.builder()
//                .id("testId")
//                .roomId("testRoomId")
//                .content("testContent")
//                .createdAt(1000)
//                .userId("testUserId")
//                .build();
//
//        testContainer.chatMessageRepository.save(chatMessage);
//    }
//
//    @Test
//    public void 채팅메세지_생성(){
//        CreateChatMessageRequest rq = CreateChatMessageRequest.builder()
//                .roomId("testRoomId1")
//                .userId("testUserId1")
//                .content("testContent1").build();
//        ResponseEntity<RestResponse<ChatMessage>> result = testContainer.chatMessageController.createChatMessage(rq);
//
//        assertThat(result.getBody().getData().getId()).isEqualTo("testId");
//        assertThat(result.getBody().getData().getUserId()).isEqualTo("testUserId1");
//        assertThat(result.getBody().getData().getRoomId()).isEqualTo("testRoomId1");
//        assertThat(result.getBody().getData().getContent()).isEqualTo("testContent1");
//        assertThat(result.getBody().getData().getCreatedAt()).isEqualTo(1000);
//    }
//
//    @Test
//    public void 채팅메세지_가져오기(){
//        ResponseEntity<RestResponse<List<ChatMessage>>> result = testContainer.chatMessageController.getAllChatMesages("testRoomId");
//
//        assertThat(result.getBody().getData().size()).isEqualTo(1);
//    }
//}
