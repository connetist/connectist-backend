package org.example.chatservice.chatMessage.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageMongoRepository;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomMongoRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@Builder
public class ChatMessageServiceImpl implements ChatMessageService{

    private ChatMessageRepository chatMessageRepository;
    private UuidHolder uuidHolder;
    private ClockHolder clockHolder;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UuidHolder uuidHolder, ClockHolder clockHolder) {
        this.chatMessageRepository = chatMessageRepository;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
    }

    @Override
    public ChatMessage addMessage(CreateChatMessageRequest rq) {
        ChatMessage chatMessage = ChatMessage.createChatMessage(rq,uuidHolder,clockHolder);

        chatMessage = chatMessageRepository.save(chatMessage);

        return chatMessage;

    }

    @Override
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll().orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
    }
}
