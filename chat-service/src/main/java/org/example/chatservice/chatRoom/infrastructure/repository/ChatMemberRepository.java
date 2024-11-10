package org.example.chatservice.chatRoom.infrastructure.repository;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatMemberEntity;

import java.util.Optional;

public interface ChatMemberRepository {
    Optional<ChatMember> findByUserId(String userId);
    ChatMember save(ChatMember chatMember);
    boolean existsByUserId(String userId);
}
