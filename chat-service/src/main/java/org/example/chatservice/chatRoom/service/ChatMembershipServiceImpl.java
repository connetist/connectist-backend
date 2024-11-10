package org.example.chatservice.chatRoom.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoomMembership;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatMemberRepository;
import org.example.chatservice.error.GlobalException;
import org.example.chatservice.error.ResultCode;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class ChatMembershipServiceImpl implements ChatMembershipService {

    private final  ChatMemberRepository chatMemberRepository;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public ChatMembershipServiceImpl(ChatMemberRepository chatMemberRepository,
                                  UuidHolder uuidHolder,
                                  ClockHolder clockHolder) {
        this.chatMemberRepository = chatMemberRepository;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
    }

    /**
     * 사용자가 채팅방에 입장
     *
     * @param userId 사용자 ID
     * @param roomId 채팅방 ID
     */
    @Transactional
    public void joinChatRoom(String userId, String roomId) {
        // 사용자 ID로 ChatMember 찾기
        ChatMember chatMember = chatMemberRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ResultCode.UNAUTHROIZED));

        // 채팅방 입장
        chatMember.joinChatRoom(roomId, clockHolder);
        chatMemberRepository.save(chatMember);
    }

    /**
     * 사용자가 채팅방에서 퇴장
     *
     * @param userId 사용자 ID
     * @param roomId 채팅방 ID
     */
    @Transactional
    public void leaveChatRoom(String userId, String roomId) {
        // 사용자 ID로 ChatMember 찾기
        ChatMember chatMember = chatMemberRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ResultCode.UNAUTHROIZED));

        // 채팅방 퇴장
        chatMember.leaveChatRoom(roomId);
        chatMemberRepository.save(chatMember);
    }

    /**
     * 특정 사용자가 속한 모든 채팅방 조회
     *
     * @param userId 사용자 ID
     * @return 채팅방 멤버십 목록
     */
    public List<ChatRoomMembership> getChatRooms(String userId) {
        ChatMember chatMember = chatMemberRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ResultCode.UNAUTHROIZED));
        return chatMember.getChatRoomMemberships();
    }



    @Transactional
    public ChatMember createChatMember(String userId) {
        ChatMember chatMember = ChatMember.createChatMember(userId, uuidHolder, clockHolder);
        return chatMemberRepository.save(chatMember);
    }

    @Override
    public boolean isUserExists(String userId) {
        return chatMemberRepository.existsByUserId(userId);
    }

}
