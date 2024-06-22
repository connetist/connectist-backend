package org.example.chatservice.chatRoom.controller;


import lombok.Builder;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.*;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.example.chatservice.chatRoom.dto.Response.RestResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

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
    public ResponseEntity<RestResponse<String>> status() {
        String str = "Chat-service는" + serverPort + "에서 실행 중입니다";
        RestResponse<String> response = RestResponse.success(str);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //GET
    @GetMapping("/rooms")
    public ResponseEntity<RestResponse<List<ChatRoom>>> getAllChatRooms(){
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        RestResponse<List<ChatRoom>> response = RestResponse.success(chatRooms);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<RestResponse<ChatRoom>> getChatRoom(@PathVariable String roomId){
        ChatRoom chatRoom = chatRoomService.getChatRoom(roomId);
        RestResponse<ChatRoom> response = RestResponse.success(chatRoom);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Create
    @PostMapping("/room")
    public ResponseEntity<RestResponse<ChatRoom>> createChatRoom(@RequestBody CreateChatRoomRequest rq){
        ChatRoom chatRoom = chatRoomService.createChatRoom(rq);
        RestResponse<ChatRoom> response = RestResponse.success(chatRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/room")
    public ResponseEntity<RestResponse<ChatRoom>> updateChatRoom(@RequestBody UpdateChatRoomRequest rq){
        ChatRoom chatRoom = chatRoomService.updateChatRoom(rq);

        RestResponse<ChatRoom> response = RestResponse.success(chatRoom);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/room")
    public ResponseEntity<RestResponse<ChatRoom>> deleteChatRoom(@RequestBody DeleteChatRoomRequest rq){
        chatRoomService.deleteChatRoom(rq);
        RestResponse<ChatRoom> response = RestResponse.success();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/member")
    public ResponseEntity<RestResponse<ChatRoom>> deleteMember(@RequestBody DeleteMemberRequest rq){
        ChatRoom chatRoom = chatRoomService.deleteMember(rq.getRoomId(),rq.getUserId());
        RestResponse<ChatRoom> response = RestResponse.success(chatRoom);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/member")
    public ResponseEntity<RestResponse<ChatRoom>> addMember(@RequestBody UpdateMemberRequest rq){
        ChatRoom chatRoom = chatRoomService.addMember(rq.getRoomId(),rq.getUserId());
        RestResponse<ChatRoom> response = RestResponse.success(chatRoom);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
