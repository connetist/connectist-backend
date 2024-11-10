package org.example.chatservice.chatRoom.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMembership {
    private String roomId;
    private long joinedAt; // 입장 시점의 타임스탬프 (밀리초 단위)
}