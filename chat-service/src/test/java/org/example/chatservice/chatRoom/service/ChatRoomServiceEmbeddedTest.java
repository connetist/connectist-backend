package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.domain.ChatRoomMembership;
import org.example.chatservice.chatRoom.dto.Request.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatMemberRepository;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.mock.TestUuidHolder;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.ClockHolderImpl;
import org.example.chatservice.utils.UuidHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ChatRoomServiceEmbeddedTest {
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatMembershipService chatMembershipService;
    @Qualifier("clockHolder")
    @Autowired
    private ClockHolder clockHolder;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UuidHolder uuidHolder() {
            return new TestUuidHolder("testId");
        }

        @Bean
        public ClockHolder clockHolder() {
            return new ClockHolderImpl();
        }

        @Bean
        public ChatMembershipServiceImpl chatMembershipService(ChatMemberRepository chatMemberRepository,
                                                               UuidHolder uuidHolder,
                                                               ClockHolder clockHolder) {
            return new ChatMembershipServiceImpl(
                    chatMemberRepository,
                    uuidHolder,
                    clockHolder
            );

        }

        @Bean
        public ChatRoomServiceImpl chatRoomService(ChatRoomRepository chatRoomRepository,
                                                   UuidHolder uuidHolder,
                                                   ClockHolder clockHolder, ChatMembershipServiceImpl chatMembershipServiceImpl) {
            return new ChatRoomServiceImpl(
                    chatRoomRepository,
                    uuidHolder,
                    clockHolder,
                    chatMembershipServiceImpl
            );
        }
    }
    @BeforeEach
    void init() {
        chatRoomRepository.deleteAll();
//        chatMemberRepository.deleteAll();
        // 초기 데이터 설정
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
        member.joinChatRoom("testId1",clockHolder);
        member.joinChatRoom("testId2",clockHolder);
        chatMemberRepository.save(member);

    }

    @Test
    public void 채팅방생성() {

        CreateChatRoomRequest rq = CreateChatRoomRequest.builder()
                .title("testTitle")
                .adminId("testAdminId")
                .departure("testDeparture")
                .destination("testDestination")
                .startTime(1000)
                .timeTaken(1000)
                .fee(1000)
                .build();
        ChatRoom newChatRoom = chatRoomService.createChatRoom(rq);


        assertThat(newChatRoom.getTitle()).isEqualTo("testTitle");
        assertThat(newChatRoom.getChatMembers().size()).isEqualTo(1);
        assertThat(newChatRoom.getAdmin().getUserId()).isEqualTo("testAdminId");
        assertThat(newChatRoom.getDeparature()).isEqualTo("testDeparture");
        assertThat(newChatRoom.getDestination()).isEqualTo("testDestination");
        assertThat(newChatRoom.getTimeTaken()).isEqualTo(1000);
        assertThat(newChatRoom.getStartTime()).isEqualTo(1000);
        assertThat(newChatRoom.getFee()).isEqualTo(1000);

        // 관리자 유저가 생성되었는지 확인
        assertThat(chatMembershipService.isUserExists("testAdminId")).isTrue();
        // 관리자 유저가 채팅방에 참여했는지 확인
        List<ChatRoomMembership> memberships = chatMembershipService.getChatRooms("testAdminId");
        assertThat(memberships.get(0).getRoomId()).isEqualTo("testId"); // Assuming newChatRoom.getId() is "testId3"
    }

    @Test
    public void 채팅방_개별_조회() {

        ChatRoom chatRoom = chatRoomService.getChatRoom("testId1");

        assertThat(chatRoom.getId()).isEqualTo("testId1");
        assertThat(chatRoom.getTitle()).isEqualTo("testTitle1");
        assertThat(chatRoom.getDeparature()).isEqualTo("testDeparture1");
        assertThat(chatRoom.getDestination()).isEqualTo("testDestination1");
        assertThat(chatRoom.getTimeTaken()).isEqualTo(100);
        assertThat(chatRoom.getStartTime()).isEqualTo(100);
        assertThat(chatRoom.getFee()).isEqualTo(1000);
    }

    @Test
    public void 채팅방_전체_조회() {

        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        for (ChatRoom chatRoom : chatRooms) {
            System.out.println(chatRoom.getId());
        }
        assertThat(chatRooms.size()).isEqualTo(2);
    }

    @Test
    public void 특정_채팅방_삭제() {

        DeleteChatRoomRequest rq = new DeleteChatRoomRequest("testId1", "memberUserId");

        chatRoomService.deleteChatRoom(rq);

        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        System.out.println(chatRooms.size());
    }

}
