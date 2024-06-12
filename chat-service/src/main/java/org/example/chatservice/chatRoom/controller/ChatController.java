package org.example.chatservice.chatRoom.controller;


import lombok.Builder;
import org.apache.coyote.Response;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/chat")
public class ChatController {
    private ChatRoomService chatRoomService;
    @Value("${server.port}")
    private String serverPort;

    @Builder
    public ChatController(ChatRoomService chatService) {
        this.chatRoomService = chatService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok()
                .body("Chat-service는 " + serverPort + "에서 실행 중입니다.");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getAllChatRooms(){
        return ResponseEntity
                .ok()
                .body(chatRoomService.getAllChatRooms());
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreateChatRoomRequest rq){

        return null;
    }



}
