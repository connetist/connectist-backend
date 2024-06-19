package org.example.chatservice.chatRoom.controller;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.*;
import org.example.chatservice.mock.TestContainer;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.chatRoom.dto.Response.RestResponse;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        ResponseEntity<RestResponse<String>> result = testContainer.chatController.status();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void 모든_방_조회(){
        ResponseEntity<RestResponse<List<ChatRoom>>> result = testContainer.chatController.getAllChatRooms();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(2);
    }

    @Test
    public void 특정_방_조회(){

        ResponseEntity<RestResponse<ChatRoom>> result = testContainer.chatController.getChatRoom("testId1");
        System.out.println(result);

        assertThat(result.getBody().getData().getId()).isEqualTo("testId1");
        assertThat(result.getBody().getData().getTitle()).isEqualTo("testTitle1");
        assertThat(result.getBody().getData().getDeparature()).isEqualTo("testDeparture1");
        assertThat(result.getBody().getData().getDestination()).isEqualTo("testDestination1");
        assertThat(result.getBody().getData().getStartTime()).isEqualTo(100);
        assertThat(result.getBody().getData().getTimeTaken()).isEqualTo(100);
        assertThat(result.getBody().getData().getFee()).isEqualTo(1000);
        assertThat(result.getBody().getData().getChatMembers().size()).isEqualTo(1);

    }

    @Test
    public void 특정방_조회시_없을떄(){
        ResponseEntity<RestResponse<ChatRoom>> result;
        try {
            result = testContainer.chatController.getChatRoom("Hello");
        } catch (GlobalException ex) {
            assertThat(ex.getResultCode()).isEqualTo(ResultCode.CHAT_ROOM_NOT_FOUND);
        }
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

        ResponseEntity<RestResponse<ChatRoom>> result = testContainer.chatController.createChatRoom(rq);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().getAdmin().getUserId()).isEqualTo("testId3");
        assertThat(result.getBody().getData().getTitle()).isEqualTo("testTitle3");
        assertThat(result.getBody().getData().getDeparature()).isEqualTo("testDeparture3");
        assertThat(result.getBody().getData().getDestination()).isEqualTo("testDestination3");
        assertThat(result.getBody().getData().getTimeTaken()).isEqualTo(200);
        assertThat(result.getBody().getData().getStartTime()).isEqualTo(200);
        assertThat(result.getBody().getData().getFee()).isEqualTo(2000);
    }

    @Test
    public void 채팅방_삭제(){
        DeleteChatRoomRequest rq = DeleteChatRoomRequest.builder().
                roomId("testId1").
                userId("memberId").
                build();

        ResponseEntity<RestResponse<ChatRoom>> result= testContainer.chatController.deleteChatRoom(rq);

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

        ResponseEntity<RestResponse<ChatRoom>> result = testContainer.chatController.updateChatRoom(rq);

        assertThat(result.getBody().getData().getId()).isEqualTo("testId1");
//        assertThat(result.getBody().getAdmin())
        assertThat(result.getBody().getData().getTitle()).isEqualTo("cTitle");
        assertThat(result.getBody().getData().getDeparature()).isEqualTo("cDeparture");
        assertThat(result.getBody().getData().getDestination()).isEqualTo("cDestination");
        assertThat(result.getBody().getData().getTimeTaken()).isEqualTo(300);
        assertThat(result.getBody().getData().getStartTime()).isEqualTo(300);
        assertThat(result.getBody().getData().getFee()).isEqualTo(3000);
    }

    @Test
    public void 채팅방_멤버_추가(){
        UpdateMemberRequest rq = UpdateMemberRequest.builder().
                userId("memberUserId2")
                .roomId("testId1")
                .build();

        ResponseEntity<RestResponse<ChatRoom>> result = testContainer.chatController.addMember(rq);

        assertThat(result.getBody().getData().getChatMembers().size()).isEqualTo(2);


    }

    @Test
    public void 채팅방_멤버_삭제(){

        DeleteMemberRequest rq = DeleteMemberRequest.builder()
                .userId("memberUserId")
                .roomId("testId1")
                .build();

        ResponseEntity<RestResponse<ChatRoom>> result = testContainer.chatController.deleteMember(rq);


        assertThat(result.getBody().getData().getChatMembers().size()).isEqualTo(0);
        assertThat(result.getBody().getData().getId()).isEqualTo("testId1");
        assertThat(result.getBody().getData().getTitle()).isEqualTo("testTitle1");
        assertThat(result.getBody().getData().getDeparature()).isEqualTo("testDeparture1");
        assertThat(result.getBody().getData().getDestination()).isEqualTo("testDestination1");
        assertThat(result.getBody().getData().getStartTime()).isEqualTo(100);
        assertThat(result.getBody().getData().getTimeTaken()).isEqualTo(100);
        assertThat(result.getBody().getData().getFee()).isEqualTo(1000);

    }

}
