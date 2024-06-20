//package org.example.chatservice.websocket;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.chatservice.chatMessage.domain.ChatMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//import org.springframework.web.socket.sockjs.client.SockJsClient;
//import org.springframework.web.socket.sockjs.client.Transport;
//import org.springframework.web.socket.sockjs.client.WebSocketTransport;
//
//import java.lang.reflect.Type;
//import java.net.http.WebSocket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EmbeddedKafka(partitions = 1, topics = { "chatting" })
//public class WebsocketTest {
//
//    @Value("${local.server.port}")
//    private int port;
//    private String URL;
//
//    private static final String SEND_CHAT_ENDPOINT = "/pub/chat";
//    private static final String SUBSCRIBE_CHATROOM_ENDPOINT = "/sub/chatroom/";
//    private CompletableFuture<ChatMessage> completableFuture;
//
//    @BeforeEach
//    public void setup() {
//        URL = "ws://localhost:" + port + "/chatting";
//        completableFuture = new CompletableFuture<>();
//    }
//
//    @Test
//    public void testSendMessage() throws Exception {
//        String roomId = "testRoomId";
//
//        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
//        }).get(5, TimeUnit.SECONDS);
//        System.out.println("STOMP session established: " + stompSession.isConnected());
//        stompSession.subscribe(SUBSCRIBE_CHATROOM_ENDPOINT + roomId, new ChatMessageStompFrameHandler());
//        System.out.println("Subscribed to: " + SUBSCRIBE_CHATROOM_ENDPOINT + roomId);
//
//        // 구독이 완료될 때까지 기다림
//        TimeUnit.SECONDS.sleep(2);  // 대기 시간을 2초로 늘림
//
//        ChatMessage chatMessage = ChatMessage.builder()
//                .id(UUID.randomUUID().toString())
//                .roomId(roomId)
//                .content("Hello, World!")
//                .userId("testUserId")
//                .createdAt(System.currentTimeMillis())
//                .build();
//
//        System.out.println("Sending message: " + chatMessage);
//        stompSession.send(SEND_CHAT_ENDPOINT, chatMessage);
//
//        try {
//            ChatMessage receivedMessage = completableFuture.get(10, TimeUnit.SECONDS);  // 대기 시간을 10초로 늘림
//            System.out.println("Received message in test: " + receivedMessage);
//            assertThat(receivedMessage).isNotNull();
//            assertThat(receivedMessage.getContent()).isEqualTo("Hello, World!");
//        } catch (Exception e) {
//            System.err.println("Failed to receive message: " + e.getMessage());
//            e.printStackTrace();
//            assertThat(false).isTrue(); // 테스트 실패를 명시적으로 표시
//        }
//    }
//
//
//    private List<Transport> createTransportClient() {
//        List<Transport> transports = new ArrayList<>(1);
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        return transports;
//    }
//
//    private class ChatMessageStompFrameHandler implements StompFrameHandler {
//        @Override
//        public Type getPayloadType(StompHeaders stompHeaders) {
//            System.out.println("Handling frame headers: " + stompHeaders);
//            return ChatMessage.class;
//        }
//
//        @Override
//        public void handleFrame(StompHeaders stompHeaders, Object payload) {
//            System.out.println("Received frame: " + stompHeaders + ", payload: " + payload);
//            if (payload instanceof ChatMessage) {
//                ChatMessage message = (ChatMessage) payload;
//                System.out.println("Completing future with message: " + message);
//                completableFuture.complete(message);
//            } else {
//                System.err.println("Unexpected payload type: " + payload.getClass());
//            }
//        }
//    }
//}
