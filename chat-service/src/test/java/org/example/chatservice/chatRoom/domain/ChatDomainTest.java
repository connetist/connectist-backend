package org.example.chatservice.chatRoom.domain;

import org.example.chatservice.chatRoom.dto.Request.UpdateChatRoomRequest;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChatDomainTest {

    private UuidHolder uuidHolder = new UuidHolderImpl();
    private ClockHolder clockHolder = new ClockHolderImpl();

    @Test
    public void 채팅멤버_도메인_생성(){
        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();

        assertThat(member.getId()).isEqualTo("memberId");
        assertThat(member.getUserId()).isEqualTo("memberUserId");
        assertThat(member.getCreatedAt()).isEqualTo(100);
        assertThat(member.getLastMessageIdx()).isEqualTo(0);
    }

    @Test
    public void 채팅방_도메인_생성(){
        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);


        ChatRoom chatRoom = ChatRoom.builder().
                id("testId").
                title("testTitle").
                chatMembers(chatMemberList).
                deparature("testDeparture").
                destination("testDestination").
                timeTaken(100).
                startTime(100).
                fee(1000).
                createdAt(10000).
                build();
//        ChatRoom chatRoom = ChatRoomGenerate();

        assertThat(chatRoom.getId()).isEqualTo("testId");
        assertThat(chatRoom.getTitle()).isEqualTo("testTitle");
        assertThat(chatRoom.getChatMembers()).isEqualTo(chatMemberList);
//        assertThat(chatRoom.getAdmin()).isEqualTo(member);
        assertThat(chatRoom.getDeparature()).isEqualTo("testDeparture");
        assertThat(chatRoom.getDestination()).isEqualTo("testDestination");
        assertThat(chatRoom.getTimeTaken()).isEqualTo(100);
        assertThat(chatRoom.getStartTime()).isEqualTo(100);
        assertThat(chatRoom.getFee()).isEqualTo(1000);
        assertThat(chatRoom.getCreatedAt()).isEqualTo(10000);
    }

    @Test
    public void 채팅방_도메인_업데이트(){
        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);


        ChatRoom chatRoom = ChatRoom.builder().
                id("testId").
                title("testTitle").
                chatMembers(chatMemberList).
                deparature("testDeparture").
                destination("testDestination").
                timeTaken(100).
                startTime(100).
                fee(1000).
                createdAt(10000).
                build();

        UpdateChatRoomRequest rq = UpdateChatRoomRequest.builder().
                id("testId").
                title("cTitle").
                departure("cDeparture").
                destination("cDestination").
                timeTaken(200).
                startTime(200).
                fee(2000).
                build();


        ChatRoom updatedChatRoom = chatRoom.updateRoom(rq);


        assertThat(updatedChatRoom.getId()).isEqualTo("testId");
        assertThat(updatedChatRoom.getTitle()).isEqualTo("cTitle");
        assertThat(updatedChatRoom.getChatMembers()).isEqualTo(chatMemberList);
//        assertThat(updatedChatRoom.getAdmin()).isEqualTo(member);
        assertThat(updatedChatRoom.getDeparature()).isEqualTo("cDeparture");
        assertThat(updatedChatRoom.getDestination()).isEqualTo("cDestination");
        assertThat(updatedChatRoom.getTimeTaken()).isEqualTo(200);
        assertThat(updatedChatRoom.getStartTime()).isEqualTo(200);
        assertThat(updatedChatRoom.getFee()).isEqualTo(2000);
        assertThat(updatedChatRoom.getCreatedAt()).isEqualTo(10000);




    }

    @Test
    public void 채팅방_멤버_추가(){
        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);


        ChatRoom chatRoom = ChatRoom.builder().
                id("testId").
                title("testTitle").
                chatMembers(chatMemberList).
                deparature("testDeparture").
                destination("testDestination").
                timeTaken(100).
                startTime(100).
                fee(1000).
                createdAt(10000).
                build();

       ChatRoom newChatRoom = chatRoom.addMember("testId2",uuidHolder,clockHolder);
       assertThat(newChatRoom.getChatMembers().size()).isEqualTo(2);

    }

    public void 채팅방_멤버_나가기(){
        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        ChatMember member2 = ChatMember.builder()
                .id("memberId2")
                .userId("memberUserId2")
                .createdAt(200)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);
        chatMemberList.add(member2);


        ChatRoom chatRoom = ChatRoom.builder().
                id("testId").
                title("testTitle").
                chatMembers(chatMemberList).
                deparature("testDeparture").
                destination("testDestination").
                timeTaken(100).
                startTime(100).
                fee(1000).
                createdAt(10000).
                build();

        ChatRoom newChatRoom = chatRoom.deleteMember("memberId2");

        assertThat(newChatRoom.getChatMembers().size()).isEqualTo(1);

    }




}
