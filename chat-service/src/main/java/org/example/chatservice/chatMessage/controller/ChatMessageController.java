package org.example.chatservice.chatMessage.controller;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.chatMessage.service.ChatMessageService;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Response.RestResponse;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/chat")
public class ChatMessageController {

    private ChatMessageService chatMessageService;
    @Value("${server.port}")
    private String serverPort;

    @Builder
    public ChatMessageController(ChatMessageService chatMessageService){
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/message")
    public ResponseEntity<RestResponse<ChatMessage>> createChatMessage(@RequestBody CreateChatMessageRequest rq){
        ChatMessage chatMessage = chatMessageService.addMessage(rq);
        RestResponse<ChatMessage> response = RestResponse.success(chatMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<RestResponse<List<ChatMessage>>> getAllChatMesages(@PathVariable String roomId){
        List<ChatMessage> chatMessages = chatMessageService.getAllMessages(roomId);

        RestResponse<List<ChatMessage>> response = RestResponse.success(chatMessages);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
