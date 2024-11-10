package org.example.chatservice.chatRoom.infrastructure.repository;


import lombok.RequiredArgsConstructor;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatMemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepositoryImpl implements ChatMemberRepository {
    private final ChatMemberMongoRepository chatMemberMongoRepository;

    @Override
    public Optional<ChatMember> findByUserId(String userId) {
        return chatMemberMongoRepository.findByUserId(userId)
                .map(ChatMemberEntity::toModel);
    }

    public ChatMember save(ChatMember chatMember) {
        return chatMemberMongoRepository.save(ChatMemberEntity.from(chatMember)).toModel();
    }

    @Override
    public boolean existsByUserId(String userId) {
        return chatMemberMongoRepository.existsByUserId(userId);
    }
}
