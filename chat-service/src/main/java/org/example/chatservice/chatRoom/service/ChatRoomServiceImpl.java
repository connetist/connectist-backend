package org.example.chatservice.chatRoom.service;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.UpdateChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
//@RequiredArgsConstructor
@Builder
public class ChatRoomServiceImpl implements ChatRoomService{


    private ChatRoomRepository chatRoomRepository;
    private UuidHolder uuidHolder;
    private ClockHolder clockHolder;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UuidHolder uuidHolder, ClockHolder clockHolder) {
        this.chatRoomRepository = chatRoomRepository;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
    }


    @Override
    public List<ChatRoom> getAllChatRooms() {

        return chatRoomRepository.findAll().orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
    }

    @Override
    public ChatRoom getChatRoom(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
    }


    @Override
    public ChatRoom createChatRoom(CreateChatRoomRequest rq) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(rq,uuidHolder,clockHolder);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
    @Override
    public ChatRoom updateChatRoom(UpdateChatRoomRequest rq) {
        ChatRoom chatRoom = chatRoomRepository.findById(rq.getId()).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 조회할 수 없습니다"));
        if (rq.getUserId() != chatRoom.getAdmin().getUserId()){
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }
        chatRoom = chatRoom.updateRoom(rq);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
    @Override
    public void deleteChatRoom(DeleteChatRoomRequest rq) {

        ChatRoom chatRoom = chatRoomRepository.findById(rq.getRoomId()).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));

        if (chatRoom.getAdmin().getId() == rq.getUserId()){
            chatRoomRepository.deleteById(rq.getRoomId());
        }else{
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }
    }


    @Override
    public ChatRoom deleteMember(String chatRoomId, String memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
        chatRoom = chatRoom.deleteMember(memberId);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom addMember(String roomId, String userId){

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
        chatRoom= chatRoom.addMember(userId,uuidHolder,clockHolder);
        return chatRoomRepository.save(chatRoom);
    }


}
