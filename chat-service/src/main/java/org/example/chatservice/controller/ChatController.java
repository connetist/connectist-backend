package org.example.chatservice.controller;


import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;
    @Value("${server.port}")
    private String serverPort;

    @Builder
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok()
                .body("Chat-service는 " + serverPort + "에서 실행 중입니다.");
    }
}
