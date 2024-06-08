package org.example.chatservice.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.infrastructure.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{


    private ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatRoom saveMessage(ChatRoom chatRoom) {

        ChatRoomEntity chatRoomEntity = chatRepository.save(ChatRoomEntity.from(chatRoom));

        return chatRoomEntity.toModel();
    }
}
