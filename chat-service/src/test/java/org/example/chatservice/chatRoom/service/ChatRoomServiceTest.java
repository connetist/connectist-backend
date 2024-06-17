package org.example.chatservice.chatRoom.service;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.UpdateChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.mock.FakeChatRoomRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.example.chatservice.utils.UuidHolderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {


    private ChatRoomServiceImpl chatRoomService;

    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    void init(){
        chatRoomRepository = new FakeChatRoomRepository();
        UuidHolder uuidHolder = new UuidHolderImpl();
        ClockHolder clockHolder = new ClockHolderImpl();
        chatRoomService = ChatRoomServiceImpl.builder()
                .chatRoomRepository(chatRoomRepository)
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

        chatRoomRepository.save(chatRoom1);
        chatRoomRepository.save(chatRoom2);
    }
    @Test
    public void 채팅방생성() {

        ChatMember member = ChatMember.builder()
                .id("memberId")
                .userId("memberUserId")
                .createdAt(100)
                .lastMessageIdx(0)
                .build();
        List<ChatMember> chatMemberList = new ArrayList<>();
        chatMemberList.add(member);

        ChatRoom chatRoom = ChatRoom.builder()
                .id("testId3")
                .title("testTitle3")
                .admin(member)
                .chatMembers(chatMemberList)
                .deparature("testDeparture3")
                .destination("testDestination3")
                .timeTaken(100)
                .startTime(100)
                .fee(1000)
                .createdAt(10000)
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoom newChatRoom = chatRoomService.getChatRoom("testId3");

        assertThat(newChatRoom.getId()).isEqualTo(chatRoom.getId());
        assertThat(newChatRoom.getTitle()).isEqualTo(chatRoom.getTitle());
//        assertThat(newChatRoom.getChatMembers()).isEqualTo(chatMemberList);
//        assertThat(updatedChatRoom.getAdmin()).isEqualTo(member);
        assertThat(newChatRoom.getDeparature()).isEqualTo(chatRoom.getDeparature());
        assertThat(newChatRoom.getDestination()).isEqualTo(chatRoom.getDestination());
        assertThat(newChatRoom.getTimeTaken()).isEqualTo(chatRoom.getTimeTaken());
        assertThat(newChatRoom.getStartTime()).isEqualTo(chatRoom.getStartTime());
        assertThat(newChatRoom.getFee()).isEqualTo(chatRoom.getFee());
//        assertThat(newChatRoom.getCreatedAt()).isEqualTo(chatRoom.getCreatedAt());


    }
    @Test
    public void 채팅방_개별_조회(){

        ChatRoom chatRoom = chatRoomService.getChatRoom("testId1");

        assertThat(chatRoom.getId()).isEqualTo("testId1");
        assertThat(chatRoom.getTitle()).isEqualTo("testTitle1");
//        assertThat(newChatRoom.getChatMembers()).isEqualTo(chatMemberList);
//        assertThat(updatedChatRoom.getAdmin()).isEqualTo(member);
        assertThat(chatRoom.getDeparature()).isEqualTo("testDeparture1");
        assertThat(chatRoom.getDestination()).isEqualTo("testDestination1");
        assertThat(chatRoom.getTimeTaken()).isEqualTo(100);
        assertThat(chatRoom.getStartTime()).isEqualTo(100);
        assertThat(chatRoom.getFee()).isEqualTo(1000);
//        assertThat(newChatRoom.getCreatedAt()).isEqualTo(chatRoom.getCreatedAt());

    }

    @Test
    public void 채팅방_전체_조회(){

        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();

        assertThat(chatRooms.size()).isEqualTo(2);

    }

    @Test
    public void 특정_채팅방_삭제(){

        DeleteChatRoomRequest rq = new DeleteChatRoomRequest("testId1","memberId");

        chatRoomService.deleteChatRoom(rq);

        assertThrows(GlobalException.class, () -> {
            chatRoomService.getChatRoom("testId1");
        });
    }

    @Test
    public void 채팅방_업데이트(){
        UpdateChatRoomRequest rq = UpdateChatRoomRequest.builder().
                id("testId2").
                userId("memberUserId").
                title("cTitle").
                departure("cDeparture").
                destination("cDestination").
                timeTaken(200).
                startTime(200).
                fee(2000).
                build();

        ChatRoom chatRoom = chatRoomService.updateChatRoom(rq);

        assertThat(chatRoom.getTitle()).isEqualTo("cTitle");
        assertThat(chatRoom.getDeparature()).isEqualTo("cDeparture");
        assertThat(chatRoom.getDestination()).isEqualTo("cDestination");
        assertThat(chatRoom.getTimeTaken()).isEqualTo(200);
        assertThat(chatRoom.getStartTime()).isEqualTo(200);
        assertThat(chatRoom.getFee()).isEqualTo(2000);


    }

    @Test
    public void 채팅방_멤버_추가(){
        ChatRoom chatRoom = chatRoomService.addMember("testId1","newMember1");

        assertThat(chatRoom.getChatMembers().size()).isEqualTo(2);
    }

    @Test
    public void 채팅방_멤버_삭제(){

        ChatRoom chatRoom = chatRoomService.deleteMember("testId1","memberId");

        assertThat(chatRoom.getChatMembers().size()).isEqualTo(1);

    }
//
//    @Test
//    public


}
