package org.example.chatservice.chatMessage.service;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;

import java.util.List;

public interface ChatMessageService {

    //메세지 생성
    ChatMessage addMessage(CreateChatMessageRequest rq);
    //모든 메세지 받기
    List<ChatMessage> getAllMessages();
}
