package org.example.chatservice.chatMessage.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.ChatMessagePageResponse;
import org.example.chatservice.chatMessage.dto.ChatMessageResponse;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageMongoRepository;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomMongoRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.kafka.KafkaProducer;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Builder
public class ChatMessageServiceImpl implements ChatMessageService{

    private ChatMessageRepository chatMessageRepository;
    private UuidHolder uuidHolder;
    private ClockHolder clockHolder;
    private KafkaProducer kafkaProducer;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UuidHolder uuidHolder, ClockHolder clockHolder, KafkaProducer kafkaProducer) {
        this.chatMessageRepository = chatMessageRepository;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
        this.kafkaProducer=kafkaProducer;
    }

    @Override
    public ChatMessage addMessage(CreateChatMessageRequest rq) throws Exception {
        ChatMessage chatMessage = ChatMessage.createChatMessage(rq,uuidHolder,clockHolder);

        chatMessage = chatMessageRepository.save(chatMessage);

        kafkaProducer.send("chatting",chatMessage);

        return chatMessage;

    }

    @Override
    public List<ChatMessage> getAllMessages(String roomId) {
        return chatMessageRepository.findAllByRoomId(roomId).orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
    }

    @Override
    public ChatMessagePageResponse getMessagesWithCursor(String roomId, Long cursor, int limit) {
        // 기본 limit 설정
        if (limit <= 0) {
            limit = 10;
        }

        List<ChatMessage> messages;

        if (cursor == null) {
            // 커서가 없는 경우, 가장 최근 메시지부터 가져옴
            messages = chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, PageRequest.of(0, limit))
                    .orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
        } else {
            // 커서가 있는 경우, 커서 이전의 메시지부터 가져옴
            messages = chatMessageRepository.findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(roomId, cursor, PageRequest.of(0, limit))
                    .orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
        }

        // 메시지를 ChatMessageResponse DTO로 변환
        List<ChatMessageResponse> messageResponses = messages.stream()
                .map(ChatMessage::toResponse)
                .collect(Collectors.toList());

        // 다음 페이지를 위한 커서 설정
        String nextCursor = null;
        if (messages.size() == limit) {
            nextCursor = String.valueOf(messages.get(messages.size() - 1).getCreatedAt());
        }


        return ChatMessagePageResponse.builder()
                .messages(messageResponses)
                .nextCursor(nextCursor)
                .build();
    }


}
