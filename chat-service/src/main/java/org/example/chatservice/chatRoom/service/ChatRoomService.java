package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.UpdateChatRoomRequest;

import java.util.List;

public interface ChatRoomService {

    // 채팅방
    List<ChatRoom> getAllChatRooms();
    ChatRoom getChatRoom(String chatRoomId);
    ChatRoom createChatRoom(CreateChatRoomRequest rq);
    ChatRoom updateChatRoom(UpdateChatRoomRequest rq);
    void deleteChatRoom(DeleteChatRoomRequest rq);


    //멤버
    ChatRoom deleteMember(String chatRoomId, String memberId);

    ChatRoom addMember(String roomId, String userId);


    //TODO

    // 1. 방장이 채팅방 나가면 채팅방 삭제
    // 2. RuntimeException말고 다른걸로 처리
    // 3. Optional

}
