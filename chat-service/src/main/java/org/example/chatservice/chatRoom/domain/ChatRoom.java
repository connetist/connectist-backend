package org.example.chatservice.chatRoom.domain;


import lombok.Builder;
import lombok.Getter;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.UpdateChatRoomRequest;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatRoom {
    private final String id;
    private final String title;
    private final List<ChatMember> chatMembers;
    private final Member admin;
    private final String deparature;
    private final String destination;
    private final String timeTaken;
    private final String startTime;
    private final int fee;
    private final long createdAt;

    @Builder
    public ChatRoom(String id, String title, List<ChatMember> chatMembers, Member admin, String deparature, String destination, String timeTaken, String startTime, int fee, long createdAt) {
        this.id = id;
        this.title = title;
        this.chatMembers = chatMembers !=null ? chatMembers : new ArrayList<>();
        this.admin = admin;
        this.deparature = deparature;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
        this.createdAt = createdAt;
    }

    public static ChatRoom createChatRoom(CreateChatRoomRequest rq, UuidHolder uuidHolder, ClockHolder clockHolder){
        return ChatRoom.builder()
                .id(uuidHolder.random())
                .title(rq.getTitle())
                .chatMembers(new ArrayList<>())
//                .admin(rq.getAdminId())
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
                .timeTaken(rq.getTimeTaken() != null ? rq.getTimeTaken() : timeTaken)
                .startTime(rq.getStartTime() != null ? rq.getStartTime() : startTime)
                .fee(rq.getFee() != 0 ? rq.getFee() : fee)
                .build();
    }

    //채팅방 사람 추가
    public ChatRoom addMember(ChatMember chatMember){
        chatMembers.add(chatMember);
        return this;
        //        List<ChatMember> newMembers = new ArrayList<>(this.chatMembers);
//        newMembers.add(chatMember);
//        return ChatRoom.builder()
//                .id(id)
//                .title(title)
//                .chatMembers(newMembers)
//                .admin(admin)
//                .deparature(deparature)
//                .destination(destination)
//                .timeTaken(timeTaken)
//                .startTime(startTime)
//                .fee(fee)
//                .build();
    }

    //특정 유저가 채팅방 나가기
    public ChatRoom deleteMember(String id){
        chatMembers.removeIf(chatMember -> chatMember.getId().equals(id));
        return this;
//        List<ChatMember> newMembers = new ArrayList<>(this.chatMembers);
//        newMembers.removeIf(chatMember -> chatMember.getId().equals(id));
//        return ChatRoom.builder()
//                .id(id)
//                .title(title)
//                .chatMembers(newMembers)
//                .admin(admin)
//                .deparature(deparature)
//                .destination(destination)
//                .timeTaken(timeTaken)
//                .startTime(startTime)
//                .fee(fee)
//                .build();


    }













}
