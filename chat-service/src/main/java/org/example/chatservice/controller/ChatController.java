package org.example.chatservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;
}
