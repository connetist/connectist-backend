package org.example.chatservice.chatRoom.controller;


import lombok.Builder;
import org.apache.coyote.Response;
import org.example.chatservice.chatMessage.service.ChatMessageService;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.*;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ChatController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok()
                .body("Chat-service는 " + serverPort + "에서 실행 중입니다.");
    }

    //GET
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getAllChatRooms(){
        return ResponseEntity
                .ok()
                .body(chatRoomService.getAllChatRooms());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable String roomId){
        return ResponseEntity
                .ok()
                .body(chatRoomService.getChatRoom(roomId));
    }

    // Create
    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreateChatRoomRequest rq){
        ChatRoom chatRoom = chatRoomService.createChatRoom(rq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(chatRoom);
    }

    @PatchMapping("/room")
    public ResponseEntity<ChatRoom> updateChatRoom(@RequestBody UpdateChatRoomRequest rq){
        ChatRoom chatRoom = chatRoomService.updateChatRoom(rq);
        return ResponseEntity
                .ok()
                .body(chatRoom);
    }

    @PutMapping("/room")
    public ResponseEntity<ChatRoom> deleteChatRoom(@RequestBody DeleteChatRoomRequest rq){
        chatRoomService.deleteChatRoom(rq);

        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("/member")
    public ResponseEntity<ChatRoom> deleteMember(@RequestBody DeleteMemberRequest rq){
        ChatRoom chatRoom = chatRoomService.deleteMember(rq.getRoomId(),rq.getUserId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(chatRoom);
    }

    @PostMapping("/member")
    public ResponseEntity<ChatRoom> addMember(@RequestBody UpdateMemberRequest rq){
        ChatRoom chatRoom = chatRoomService.addMember(rq.getRoomId(),rq.getUserId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(chatRoom);

    }


}
