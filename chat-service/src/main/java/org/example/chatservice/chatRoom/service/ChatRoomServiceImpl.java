package org.example.chatservice.chatRoom.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.UpdateChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomMongoRepository;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return chatRoomRepository.findAll().orElseThrow(() -> new ResourceNotFoundException("전체 룸을 조회할 수 없습니다."));
    }

    @Override
    public ChatRoom getChatRoom(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 조회할 수 없습니다"));
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
        chatRoom = chatRoom.updateRoom(rq);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
    @Override
    public void deleteChatRoom(DeleteChatRoomRequest rq) {

        ChatRoom chatRoom = chatRoomRepository.findById(rq.getRoomId()).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 찾을 수 없습니다"));

        if (chatRoom.getAdmin().getId() == rq.getUserId()){
            chatRoomRepository.deleteById(rq.getRoomId());
        }else{
            throw new RuntimeException("권한이 없습니다.");
        }
    }


    @Override
    public void deleteMember(String chatRoomId, String memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 찾을 수 없습니다"));
        chatRoom = chatRoom.deleteMember(memberId);
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void addMember(String roomId, String userId){

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 찾을 수 없습니다"));
        chatRoom= chatRoom.addMember(userId,uuidHolder,clockHolder);
        chatRoomRepository.save(chatRoom);
    }


}
