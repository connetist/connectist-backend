package org.example.chatservice.chatRoom.infrastructure.entity;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoomMembership;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_members")
public class ChatMemberEntity {

    @Id
    private String id;
    private String userId;
    private int lastMessageIdx;
    private long createdAt;
    private List<ChatRoomMembership> chatRoomMemberships;

    /**
     * 도메인 모델 ChatMember로부터 ChatMemberEntity를 생성하는 메서드
     *
     * @param chatMember 도메인 모델 ChatMember
     * @return ChatMemberEntity
     */
    public static ChatMemberEntity from(ChatMember chatMember) {
        return ChatMemberEntity.builder()
                .id(chatMember.getId())
                .userId(chatMember.getUserId())
                .lastMessageIdx(chatMember.getLastMessageIdx())
                .createdAt(chatMember.getCreatedAt())
                .chatRoomMemberships(chatMember.getChatRoomMemberships())
                .build();
    }

    /**
     * ChatMemberEntity로부터 도메인 모델 ChatMember를 생성하는 메서드
     *
     * @return ChatMember 도메인 모델
     */
    public ChatMember toModel() {
        return ChatMember.builder()
                .id(this.id)
                .userId(this.userId)
                .lastMessageIdx(this.lastMessageIdx)
                .createdAt(this.createdAt)
                .chatRoomMemberships(this.chatRoomMemberships != null ? new ArrayList<>(this.chatRoomMemberships) : new ArrayList<>())
                .build();
    }
}