package org.example.chatservice.chatMessage.service;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.ChatMessagePageResponse;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
import org.example.chatservice.kafka.KafkaProducer;
import org.example.chatservice.mock.TestClockHolder;
import org.example.chatservice.mock.TestUuidHolder;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ChatMessageServiceTest {

    @Autowired
    private ChatMessageServiceImpl chatMessageService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MockBean
    private KafkaProducer kafkaProducer;


    private ClockHolder clockHolder;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ClockHolder clockHolder() {
            return new TestClockHolder(System.currentTimeMillis());
        }
        @Bean
        public UuidHolder uuidHolder() {
            return new UuidHolderImpl();
        }

        @Bean
        public ChatMessageServiceImpl chatMessageService(ChatMessageRepository chatMessageRepository,
                                                         UuidHolder uuidHolder,
                                                         ClockHolder clockHolder,
                                                         KafkaProducer kafkaProducer) {
            return ChatMessageServiceImpl.builder()
                    .chatMessageRepository(chatMessageRepository)
                    .uuidHolder(uuidHolder)
                    .clockHolder(clockHolder)
                    .kafkaProducer(kafkaProducer)
                    .build();
        }
    }

    @BeforeEach
    void init(@Autowired ClockHolder clockHolder) {
        this.clockHolder = clockHolder;
        // 기존 데이터를 삭제하여 테스트 간 데이터 독립성 보장
        chatMessageRepository.deleteAll();
        List<ChatMessage> messages = new ArrayList<>();
        // 초기 데이터 설정 (필요한 경우)
        // 예를 들어, 특정 채팅방에 메시지가 있는 경우 추가
        // 현재는 메시지 테스트를 위한 데이터 준비를 각 테스트 메서드에서 수행
        for (int i = 1; i <= 100; i++) {
            CreateChatMessageRequest rq = CreateChatMessageRequest.builder()
                    .roomId("room1")
                    .userId("user" + ((i % 10) + 1)) // user1부터 user10까지 반복
                    .content("Test message " + i)
                    .build();

            try {
                ChatMessage chatMessage = ChatMessage.createChatMessage(rq, new UuidHolderImpl(), this.clockHolder);
                messages.add(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        chatMessageRepository.saveAll(messages);

    }

    @Test
    public void 채팅방_전체_메세지_조회() {
        List<ChatMessage> messages = chatMessageService.getAllMessages("room1");
        assertThat(messages.size()).isEqualTo(100);

        // 첫 번째 메시지의 내용이 "Test message 1"인지 검증
        assertThat(messages.get(0).getContent()).isEqualTo("Test message 1");

        // 마지막 메시지의 내용이 "Test message 100"인지 검증
        assertThat(messages.get(99).getContent()).isEqualTo("Test message 100");

    }

    @Test
    public void 채팅메시지생성_성공() throws Exception {
        CreateChatMessageRequest rq = CreateChatMessageRequest.builder()
                .roomId("room1")
                .userId("user1")
                .content("Hello, World!")
                .build();

        ChatMessage newChatMessage = chatMessageService.addMessage(rq);

        // 저장된 메시지 검증
        assertThat(newChatMessage.getRoomId()).isEqualTo("room1");
        assertThat(newChatMessage.getUserId()).isEqualTo("user1");
        assertThat(newChatMessage.getContent()).isEqualTo("Hello, World!");
        assertThat(newChatMessage.getId()).isNotNull(); // UUID가 설정되었는지 확인
        assertThat(newChatMessage.getCreatedAt()).isNotNull(); // 시각이 설정되었는지 확인

        // KafkaProducer.send 호출 검증
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ChatMessage> messageCaptor = ArgumentCaptor.forClass(ChatMessage.class);
        verify(kafkaProducer, times(1)).send(topicCaptor.capture(), messageCaptor.capture());

        assertThat(topicCaptor.getValue()).isEqualTo("chatting");
        assertThat(messageCaptor.getValue()).isEqualTo(newChatMessage);
    }


    @Test
    public void 채팅방의모든메시지조회_성공() throws Exception {
        // 메시지 저장
        ChatMessage msg1 = ChatMessage.createChatMessage(
                CreateChatMessageRequest.builder()
                        .roomId("room1")
                        .userId("user1")
                        .content("Hello!")
                        .build(),
                new TestUuidHolder("testId1"),
                new ClockHolderImpl()
        );
        ChatMessage msg2 = ChatMessage.createChatMessage(
                CreateChatMessageRequest.builder()
                        .roomId("room1")
                        .userId("user2")
                        .content("Hi there!")
                        .build(),
                new TestUuidHolder("testId2"),
                new ClockHolderImpl()
        );

        chatMessageRepository.save(msg1);
        chatMessageRepository.save(msg2);

        // 메시지 조회
        List<ChatMessage> messages = chatMessageService.getAllMessages("room1");

        // 메시지 검증
        assertThat(messages.size()).isEqualTo(102);
    }

    @Test
    public void 커서기반_페이징_첫_페이지_조회() {
        ChatMessagePageResponse response = chatMessageService.getMessagesWithCursor("room1", null, 10);

        assertThat(response.getMessages().size()).isEqualTo(10);
        assertThat(response.getMessages().get(0).getContent()).isEqualTo("Test message 100"); // 최신 메시지
        assertThat(response.getMessages().get(9).getContent()).isEqualTo("Test message 91");
        assertThat(response.getNextCursor()).isNotNull();

        // 다음 페이지의 커서가 올바른지 확인
        long expectedNextCursor = response.getMessages().get(9).getCreatedAt();
        assertThat(Long.parseLong(response.getNextCursor())).isEqualTo(expectedNextCursor);
    }

    /**
     * 커서 기반 페이징 두 번째 페이지 조회 테스트
     */
    @Test
    public void 커서기반_페이징_두번째_페이지_조회() {
        // 첫 번째 페이지 조회
        ChatMessagePageResponse firstPage = chatMessageService.getMessagesWithCursor("room1", null, 10);
        Long cursor = Long.parseLong(firstPage.getNextCursor());

        // 두 번째 페이지 조회
        ChatMessagePageResponse secondPage = chatMessageService.getMessagesWithCursor("room1", cursor, 10);

        assertThat(secondPage.getMessages().size()).isEqualTo(10);
        assertThat(secondPage.getMessages().get(0).getContent()).isEqualTo("Test message 90");
        assertThat(secondPage.getMessages().get(9).getContent()).isEqualTo("Test message 81");
        assertThat(secondPage.getNextCursor()).isNotNull();

        // 다음 페이지의 커서가 올바른지 확인
        long expectedNextCursor = secondPage.getMessages().get(9).getCreatedAt();
        assertThat(Long.parseLong(secondPage.getNextCursor())).isEqualTo(expectedNextCursor);
    }

    /**
     * 커서 기반 페이징 세 번째 페이지 조회 테스트
     */
    @Test
    public void 커서기반_페이징_세번째_페이지_조회() {
        // 첫 번째 페이지 조회
        ChatMessagePageResponse firstPage = chatMessageService.getMessagesWithCursor("room1", null, 10);
        Long cursor1 = Long.parseLong(firstPage.getNextCursor());

        // 두 번째 페이지 조회
        ChatMessagePageResponse secondPage = chatMessageService.getMessagesWithCursor("room1", cursor1, 10);
        Long cursor2 = Long.parseLong(secondPage.getNextCursor());

        // 세 번째 페이지 조회
        ChatMessagePageResponse thirdPage = chatMessageService.getMessagesWithCursor("room1", cursor2, 10);

        assertThat(thirdPage.getMessages().size()).isEqualTo(10);
        assertThat(thirdPage.getMessages().get(0).getContent()).isEqualTo("Test message 80");
        assertThat(thirdPage.getMessages().get(9).getContent()).isEqualTo("Test message 71");
        assertThat(thirdPage.getNextCursor()).isNotNull();
    }





}