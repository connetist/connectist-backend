package org.example.chatservice.chatMessage.controller;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.chatMessage.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {

    private ChatMessageService chatMessageService;
    @Builder
    public WebSocketController(ChatMessageService chatMessageService){
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat")
    public void createChatMessage( CreateChatMessageRequest rq) throws Exception {
        System.out.println(rq.getContent());
        ChatMessage chatMessage = chatMessageService.addMessage(rq);
        System.out.println("CHAT MESSAGE: " + chatMessage.toString());

    }
}
