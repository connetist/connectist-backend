package org.example.chatservice.mock;

import lombok.Builder;
import org.example.chatservice.chatMessage.controller.ChatMessageController;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
import org.example.chatservice.chatMessage.service.ChatMessageService;
import org.example.chatservice.chatMessage.service.ChatMessageServiceImpl;
import org.example.chatservice.chatRoom.controller.ChatController;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.example.chatservice.chatRoom.service.ChatRoomService;
import org.example.chatservice.chatRoom.service.ChatRoomServiceImpl;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestContainer {
    public final ChatController chatController;
    public final ChatRoomRepository chatRoomRepository;
    public final ChatRoomService chatRoomService;

    public final ChatMessageRepository chatMessageRepository;

    public final ChatMessageService chatMessageService;

    public final ChatMessageController chatMessageController;

    private final UuidHolder uuidHolder;

    private final ClockHolder clockHolder;
    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.uuidHolder= uuidHolder;
        this.clockHolder = clockHolder;
        this.chatRoomRepository = new FakeChatRoomRepository();
        this.chatMessageRepository = new FakeChatMessageRepository();
        this.chatMessageService = ChatMessageServiceImpl.builder()
                .chatMessageRepository(chatMessageRepository)
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();

        this.chatRoomService = ChatRoomServiceImpl.builder()
                .chatRoomRepository(chatRoomRepository)
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();
        this.chatController = ChatController.builder()
                .chatRoomService(chatRoomService)
                .build();

        this.chatMessageController = ChatMessageController.builder()
                .chatMessageService(chatMessageService)
                .build();






    }



}
