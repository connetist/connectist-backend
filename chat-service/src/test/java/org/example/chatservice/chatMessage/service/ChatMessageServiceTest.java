//package org.example.chatservice.chatMessage.service;
//
//import org.example.chatservice.chatMessage.domain.ChatMessage;
//import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
//import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
//import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
//import org.example.chatservice.chatRoom.service.ChatRoomServiceImpl;
//import org.example.chatservice.mock.FakeChatMessageRepository;
//import org.example.chatservice.mock.TestClockHolder;
//import org.example.chatservice.mock.TestUuidHolder;
//import org.example.chatservice.utils.ClockHolder;
//import org.example.chatservice.utils.UuidHolder;
//import org.example.chatservice.utils.UuidHolderImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javax.swing.text.html.Option;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//
//public class ChatMessageServiceTest {
//
//    private ChatMessageServiceImpl chatMessageService;
//
//    private ChatMessageRepository chatMessageRepository;
//
//    @BeforeEach
//    void init(){
//        chatMessageRepository = new FakeChatMessageRepository();
//        UuidHolder uuidHolder = new TestUuidHolder("testId");
//        ClockHolder clockHolder = new TestClockHolder(1000);
//        chatMessageService = new ChatMessageServiceImpl(chatMessageRepository,uuidHolder,clockHolder);
//
//
//        ChatMessage chatMessage = ChatMessage.builder().
//                id("testId1")
//                .userId("testUserId1")
//                .content("testContent1")
//                .roomId("testRoomId1")
//                .createdAt(1000).build();
//
//        chatMessageRepository.save(chatMessage);
//    }
//
//    @Test
//    void 채팅메세지_넣기(){
//
//        CreateChatMessageRequest rq = CreateChatMessageRequest.builder()
//                        .roomId("testRoomId1")
//                        .userId("testUserId2")
//                        .content("testContent2").build();
//
//
//        ChatMessage chatMessage = chatMessageService.addMessage(rq);
//
//        assertThat(chatMessage.getId()).isEqualTo("testId");
//        assertThat(chatMessage.getRoomId()).isEqualTo("testRoomId1");
//        assertThat(chatMessage.getUserId()).isEqualTo("testUserId2");
//        assertThat(chatMessage.getContent()).isEqualTo("testContent2");
//        assertThat(chatMessage.getCreatedAt()).isEqualTo(1000);
//    }
//
//    @Test
//    void 채팅메세지_전체_조회(){
//        List<ChatMessage> chatMessageList = chatMessageService.getAllMessages("testRoomId1");
//
//        assertThat(chatMessageList.size()).isEqualTo(1);
//
//    }
//
//
//}
