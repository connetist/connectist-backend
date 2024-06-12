package org.example.chatservice.chatRoom.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.UpdateChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{


    private ChatRoomRepository chatRoomRepository;
    private UuidHolder uuidHolder;
    private ClockHolder clockHolder;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ChatRoom createChatRoom(CreateChatRoomRequest rq) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(rq,uuidHolder,clockHolder);
        ChatRoomEntity chatRoomEntity = chatRoomRepository.save(ChatRoomEntity.from(chatRoom));
        return chatRoomEntity.toModel();
    }

    @Override
    public void deleteChatRoom(String chatRoomId) {

        chatRoomRepository.deleteById(chatRoomId);
    }

    @Override
    public ChatRoom updateChatRoom(UpdateChatRoomRequest rq) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(rq.getId())
                .orElseThrow(() -> new RuntimeException());
        ChatRoom chatRoom = chatRoomEntity.toModel();
        ChatRoom newChatRoom = chatRoom.updateRoom(rq);
        chatRoomRepository.save(ChatRoomEntity.from(newChatRoom));
        return newChatRoom;
    }

    @Override
    public void deleteMember(String chatRoomId, String memberId){
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException());

        ChatRoom chatRoom = chatRoomEntity.toModel();
        ChatRoom newChatRoom = chatRoom.deleteMember(memberId);
        chatRoomRepository.save(ChatRoomEntity.from(newChatRoom));
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findAll();
        return chatRoomEntities.stream()
                .map(ChatRoomEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoom getChatRoom(String chatRoomId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException());

        ChatRoom chatRoom = chatRoomEntity.toModel();
        return chatRoom;
    }


}
