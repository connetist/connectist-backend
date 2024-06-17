package org.example.chatservice.chatRoom.controller;

import org.apache.coyote.Response;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.*;
import org.example.chatservice.chatRoom.mock.TestContainer;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ChatControllerTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init(){
        UuidHolder uuidHolder = new UuidHolderImpl();
        ClockHolder clockHolder = new ClockHolderImpl();
        this.testContainer = TestContainer.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();


        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .id("testId1")
                .title("testTitle1")
                .admin(member)
                .chatMembers(chatMemberList)
                .deparature("testDeparture1")
                .destination("testDestination1")
                .timeTaken(100)
                .startTime(100)
                .fee(1000)
                .createdAt(10000)
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .id("testId2")
                .title("testTitle2")
                .chatMembers(chatMemberList)
                .admin(member)
                .deparature("testDeparture2")
                .destination("testDestination2")
                .timeTaken(200)
                .startTime(200)
                .fee(2000)
                .createdAt(20000)
                .build();

        testContainer.chatRoomRepository.save(chatRoom1);
        testContainer.chatRoomRepository.save(chatRoom2);
    }

    @Test
    public void 헬스체크_할_수_있다(){
        ResponseEntity<String> result = testContainer.chatController.status();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void 모든_방_조회(){
        ResponseEntity<List<ChatRoom>> result = testContainer.chatController.getAllChatRooms();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().size()).isEqualTo(2);
    }

    @Test
    public void 특정_방_조회(){

        ResponseEntity<ChatRoom> result = testContainer.chatController.getChatRoom("testId1");


        assertThat(result.getBody().getId()).isEqualTo("testId1");
        assertThat(result.getBody().getTitle()).isEqualTo("testTitle1");
        assertThat(result.getBody().getDeparature()).isEqualTo("testDeparture1");
        assertThat(result.getBody().getDestination()).isEqualTo("testDestination1");
        assertThat(result.getBody().getStartTime()).isEqualTo(100);
        assertThat(result.getBody().getTimeTaken()).isEqualTo(100);
        assertThat(result.getBody().getFee()).isEqualTo(1000);
        assertThat(result.getBody().getChatMembers().size()).isEqualTo(1);

    }

    @Test
    public void 채팅방_생성(){
        CreateChatRoomRequest rq = CreateChatRoomRequest.builder().
                adminId("testId3").
                title("testTitle3").
                departure("testDeparture3").
                destination("testDestination3").
                timeTaken(200).
                startTime(200).
                fee(2000).
                build();

        ResponseEntity<ChatRoom> result = testContainer.chatController.createChatRoom(rq);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getAdmin().getUserId()).isEqualTo("testId3");
        assertThat(result.getBody().getTitle()).isEqualTo("testTitle3");
        assertThat(result.getBody().getDeparature()).isEqualTo("testDeparture3");
        assertThat(result.getBody().getDestination()).isEqualTo("testDestination3");
        assertThat(result.getBody().getTimeTaken()).isEqualTo(200);
        assertThat(result.getBody().getStartTime()).isEqualTo(200);
        assertThat(result.getBody().getFee()).isEqualTo(2000);
    }

    @Test
    public void 채팅방_삭제(){
        DeleteChatRoomRequest rq = DeleteChatRoomRequest.builder().
                roomId("testId1").
                userId("memberId").
                build();

        ResponseEntity<ChatRoom> result= testContainer.chatController.deleteChatRoom(rq);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 채팅방_업데이트(){
        UpdateChatRoomRequest rq = UpdateChatRoomRequest.builder()
                .id("testId1")
                .userId("memberUserId")
                .title("cTitle")
                .departure("cDeparture")
                .destination("cDestination")
                .timeTaken(300)
                .startTime(300)
                .fee(3000)
                .build();

        ResponseEntity<ChatRoom> result = testContainer.chatController.updateChatRoom(rq);

        assertThat(result.getBody().getId()).isEqualTo("testId1");
//        assertThat(result.getBody().getAdmin())
        assertThat(result.getBody().getTitle()).isEqualTo("cTitle");
        assertThat(result.getBody().getDeparature()).isEqualTo("cDeparture");
        assertThat(result.getBody().getDestination()).isEqualTo("cDestination");
        assertThat(result.getBody().getTimeTaken()).isEqualTo(300);
        assertThat(result.getBody().getStartTime()).isEqualTo(300);
        assertThat(result.getBody().getFee()).isEqualTo(3000);
    }

    @Test
    public void 채팅방_멤버_추가(){
        UpdateMemberRequest rq = UpdateMemberRequest.builder().
                userId("memberUserId2")
                .roomId("testId1")
                .build();

        ResponseEntity<ChatRoom> result = testContainer.chatController.addMember(rq);

        assertThat(result.getBody().getChatMembers().size()).isEqualTo(2);


    }

    @Test
    public void 채팅방_멤버_삭제(){

        DeleteMemberRequest rq = DeleteMemberRequest.builder()
                .userId("memberUserId")
                .roomId("testId1")
                .build();

        ResponseEntity<ChatRoom> result = testContainer.chatController.deleteMember(rq);


        assertThat(result.getBody().getChatMembers().size()).isEqualTo(0);
        assertThat(result.getBody().getId()).isEqualTo("testId1");
        assertThat(result.getBody().getTitle()).isEqualTo("testTitle1");
        assertThat(result.getBody().getDeparature()).isEqualTo("testDeparture1");
        assertThat(result.getBody().getDestination()).isEqualTo("testDestination1");
        assertThat(result.getBody().getStartTime()).isEqualTo(100);
        assertThat(result.getBody().getTimeTaken()).isEqualTo(100);
        assertThat(result.getBody().getFee()).isEqualTo(1000);

    }

}
