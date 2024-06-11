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
    private final List<ChatMessage> chatMessages;
    private final Member admin;
    private final String deparature;
    private final String destination;
    private final String timeTaken;
    private final String startTime;
    private final int fee;
    private final int createdAt;

    @Builder
    public ChatRoom(String id, String title, List<ChatMember> chatMembers, Member admin, String deparature, String destination, String timeTaken, String startTime, int fee, int createdAt, List<ChatMessage> chatMessages) {
        this.id = id;
        this.title = title;
        this.ChatMembers = chatMembers;
        this.chatMessages = chatMessages;
        this.admin = admin;
        this.deparature = deparature;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
        this.createdAt = createdAt;
    }

    // 채팅방 전체 메세지 가져오기
    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    //채팅방 메세지 추가
    public void addMessage(ChatMessage chatMessage){
        this.chatMessages.add(chatMessage);
    }
    // 채팅방에 사람 추가
    public void addMember(Member member){

    }



}
