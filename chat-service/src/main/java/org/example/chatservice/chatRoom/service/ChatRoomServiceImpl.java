package org.example.chatservice.chatRoom.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.Request.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.DeleteChatRoomRequest;
import org.example.chatservice.chatRoom.dto.Request.UpdateChatRoomRequest;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService{


    private final ChatRoomRepository chatRoomRepository;
    private final ChatMembershipService chatMembershipService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UuidHolder uuidHolder, ClockHolder clockHolder, ChatMembershipService chatMembershipService) {
        this.chatRoomRepository = chatRoomRepository;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
        this.chatMembershipService = chatMembershipService;
    }


    @Override
    public List<ChatRoom> getAllChatRooms() {

        return chatRoomRepository.findAll().orElseThrow(() -> new GlobalException(ResultCode.CHAT_ROOMS_NOT_FOUND));
    }

    @Override
    public ChatRoom getChatRoom(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
    }

    // 채팅방을 생성하면서 유저가 없다면 유저 생성해야함
    @Override
    public ChatRoom createChatRoom(CreateChatRoomRequest rq) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(rq,uuidHolder,clockHolder);
        chatRoom = chatRoomRepository.save(chatRoom);

        String adminUserId = chatRoom.getAdmin().getUserId();
        if (!chatMembershipService.isUserExists(adminUserId)){
            chatMembershipService.createChatMember(adminUserId);
            log.info("관리자 유저({})가 생성되었습니다.", adminUserId);
        }
        chatMembershipService.joinChatRoom(adminUserId, chatRoom.getId());
        return chatRoom;
    }
    @Override
    public ChatRoom updateChatRoom(UpdateChatRoomRequest rq) {
        ChatRoom chatRoom = chatRoomRepository.findById(rq.getId()).orElseThrow(()-> new ResourceNotFoundException("해당 채팅방을 조회할 수 없습니다"));
        System.out.println(rq.getUserId() + " " + chatRoom.getAdmin().getUserId());
        if (!rq.getUserId().equals(chatRoom.getAdmin().getUserId())){
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }
        chatRoom = chatRoom.updateRoom(rq);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    // 채팅방을 삭제하면서 각 채팅방에 속해있는 유저들이 채팅방 삭제
    @Override
    public void deleteChatRoom(DeleteChatRoomRequest rq) {

        ChatRoom chatRoom = chatRoomRepository.findById(rq.getRoomId()).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
        System.out.println(chatRoom.getAdmin().getUserId() + " " + rq.getUserId());
        if (!chatRoom.getAdmin().getUserId().equals(rq.getUserId())) {
            System.out.println("HI");
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }

        // 채팅방 삭제 전에 모든 멤버들의 ChatMember 엔티티에서 해당 채팅방을 제거
        chatRoom.getMembers().forEach(member -> {
            chatMembershipService.leaveChatRoom(member.getUserId(), chatRoom.getId());
        });

        chatRoomRepository.deleteById(rq.getRoomId());
        log.info("Deleted ChatRoom: {}", rq.getRoomId());
    }


    // 채팅방 내에서도 사람을 빼면서 동시에 chatmember의 채팅방 리스트에서 제거해줘야함
    @Override
    public ChatRoom deleteMember(String chatRoomId, String memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
        chatRoom = chatRoom.deleteMember(memberId);

        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방에 맴버를 추가하면서 chatmember의 채팅방리스트에 해당 채팅방을 추가해야함
    @Override
    public ChatRoom addMember(String roomId, String userId){

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()-> new GlobalException(ResultCode.CHAT_ROOM_NOT_FOUND));
        chatRoom= chatRoom.addMember(userId,uuidHolder,clockHolder);

        if (!chatMembershipService.isUserExists(userId)){
            chatMembershipService.createChatMember(userId);
            log.info("유저({})가 존재하지 않아 생성되었습니다.", userId);
        }
        chatMembershipService.joinChatRoom(userId, roomId);
        log.info("유저({})가 채팅방({})에 참여했습니다.", userId, roomId);
        return chatRoomRepository.save(chatRoom);
    }


}
