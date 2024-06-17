package org.example.chatservice.chatRoom.domain;


import lombok.Builder;
import lombok.Getter;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.UpdateChatRoomRequest;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoom {
    private final String id;
    private final String title;
    private final List<ChatMember> chatMembers;
    private final ChatMember admin;
    private final String deparature;
    private final String destination;
    private final long timeTaken;
    private final long startTime;
    private final int fee;
    private final long createdAt;

    @Builder
    public ChatRoom(String id, String title, List<ChatMember> chatMembers, ChatMember admin, String deparature, String destination, long timeTaken, long startTime, int fee, long createdAt) {
        this.id = id;
        this.title = title;
        this.chatMembers = chatMembers;
        this.admin = admin;
        this.deparature = deparature;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
        this.createdAt = createdAt;
    }

    public static ChatRoom createChatRoom(CreateChatRoomRequest rq, UuidHolder uuidHolder, ClockHolder clockHolder){

        List<ChatMember> chatMemberList = new ArrayList<>();
        ChatMember newAdmin = ChatMember.createChatMember(rq.getAdminId(),uuidHolder,clockHolder);
        chatMemberList.add(newAdmin);
        return ChatRoom.builder()
                .id(uuidHolder.random())
                .title(rq.getTitle())
                .chatMembers(chatMemberList)
                .admin(newAdmin)
                .deparature(rq.getDeparture())
                .destination(rq.getDestination())
                .timeTaken(rq.getTimeTaken())
                .startTime(rq.getStartTime())
                .fee(rq.getFee())
                .createdAt(clockHolder.mills())
                .build();
    }
    //채팅방 업데이트
    public ChatRoom updateRoom(UpdateChatRoomRequest rq){

        return ChatRoom.builder()
                .id(id)
                .title(rq.getTitle() != null ? rq.getTitle() : title)
                .chatMembers(this.chatMembers)
                .admin(admin)
                .deparature(rq.getDeparture() != null ? rq.getDeparture() : deparature)
                .destination(rq.getDestination() != null ? rq.getDestination(): destination)
                .timeTaken(rq.getTimeTaken() != 0 ? rq.getTimeTaken() : timeTaken)
                .startTime(rq.getStartTime() != 0 ? rq.getStartTime() : startTime)
                .fee(rq.getFee() != 0 ? rq.getFee() : fee)
                .createdAt(createdAt)
                .build();
    }

    //채팅방 사람 추가
    public ChatRoom addMember(String userId, UuidHolder uuidHolder, ClockHolder clockHolder){
        ChatMember chatMember = ChatMember.createChatMember(userId,uuidHolder,clockHolder);
        chatMembers.add(chatMember);
        return this;
    }

    //특정 유저가 채팅방 나가기
    public ChatRoom deleteMember(String id){
        chatMembers.removeIf(chatMember -> chatMember.getUserId().equals(id));
        return this;
    }













}
