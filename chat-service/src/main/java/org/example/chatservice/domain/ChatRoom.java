package org.example.chatservice.domain;


import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Member;
import java.util.List;

@Getter
public class ChatRoom {
    private final String id;
    private final String title;
    private final List<ChatMember> ChatMembers;
    private final Member admin;
    private final String deparature;
    private final String destination;
    private final String timeTaken;
    private final String startTime;
    private final int fee;
    private final int createdAt;

    @Builder
    public ChatRoom(String id, String title, List<ChatMember> chatMembers, Member admin, String deparature, String destination, String timeTaken, String startTime, int fee, int createdAt) {
        this.id = id;
        this.title = title;
        this.ChatMembers = chatMembers;
        this.admin = admin;
        this.deparature = deparature;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
        this.createdAt = createdAt;
    }


}
