package org.example.chatservice.websocket;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.config.WebSocketConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.lang.reflect.Type;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ContextConfiguration
public class WebsocketTest {

    @Autowired
    private WebSocketConfig webSocketConfig;

    private WebSocketStompClient stompClient;
    private StompSession stompSession;

    @BeforeEach
    public void setup() throws Exception {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompSession = stompClient.connect("ws://localhost:9001/chatting", new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);
        System.out.println("STOMP:" + stompSession);
        assertThat(stompSession).isNotNull();
    }

    @Test
    public void testSendMessage() throws Exception {
        String messageContent = "Hello, World!";

        ChatMessage chatMessage = ChatMessage.builder()
                .id("testId")
                .roomId("testRoomId")
                .content("testContent")
                .userId("testUserId")
                .createdAt(10000)
                .build();

        BlockingQueue<ChatMessage> blockingQueue = new ArrayBlockingQueue<>(1);

        stompSession.subscribe("/sub/chatroom/testRoomId", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                blockingQueue.offer((ChatMessage) payload);
            }
        });

        stompSession.send("/pub/chat", chatMessage);

        ChatMessage receivedMessage = blockingQueue.poll(5, TimeUnit.SECONDS);
        System.out.println(receivedMessage);
        //        assertThat(receivedMessage).isNotNull();
//        assertThat(receivedMessage.getContent()).isEqualTo(messageContent);
    }
}
