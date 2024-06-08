package org.example.chatservice.infrastructure.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.chatservice.domain.ChatMember;
import org.example.chatservice.domain.ChatRoom;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Member;
import java.util.List;

@Getter
@Setter
@Document(collection = "chat_rooms")
@Builder
public class ChatRoomEntity {
    @Id
    private String id;
    private String title;
    private List<ChatMember> chatMembers;
    private Member admin;
    private String departure;
    private String destination;
    private String timeTaken;
    private String startTime;
    private int fee;
    private int createdAt;


    public static ChatRoomEntity from(ChatRoom chatRoom){
        return ChatRoomEntity.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .chatMembers(chatRoom.getChatMembers())
                .admin(chatRoom.getAdmin())
                .departure(chatRoom.getDeparature())
                .destination(chatRoom.getDeparature())
                .timeTaken(chatRoom.getTimeTaken())
                .startTime(chatRoom.getStartTime())
                .fee(chatRoom.getFee())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public ChatRoom toModel(){
        return ChatRoom.builder()
                .id(id)
                .title(title)
                .chatMembers(chatMembers)
                .admin(admin)
                .deparature(departure)
                .destination(destination)
                .timeTaken(timeTaken)
                .startTime(startTime)
                .fee(fee)
                .createdAt(createdAt)
                .build();
    }

}
