package org.example.chatservice.chatRoom.controller;


import lombok.Builder;
import org.apache.coyote.Response;
import org.example.chatservice.chatMessage.service.ChatMessageService;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.DeleteMemberRequest;
import org.example.chatservice.chatRoom.dto.UpdateMemberRequest;
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

        return null;
    }

    @PutMapping("/room")
    public ResponseEntity<Void> deleteChatRoom(@RequestBody DeleteChatRoomRequest rq){
        chatRoomService.deleteChatRoom(rq);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/member")
    public ResponseEntity<Void> deleteMember(@RequestBody DeleteMemberRequest rq){
        chatRoomService.deleteMember(rq.getRoomId(),rq.getUserId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/member")
    public ResponseEntity<Void> addMember(@RequestBody UpdateMemberRequest rq){
        chatRoomService.addMember(rq.getRoomId(),rq.getUserId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }


}
