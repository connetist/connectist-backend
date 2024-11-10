package org.example.chatservice.chatMessage.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatMessagePageResponse {
    private List<ChatMessageResponse> messages;
    private String nextCursor; // 다음 페이지를 위한 커서
}
