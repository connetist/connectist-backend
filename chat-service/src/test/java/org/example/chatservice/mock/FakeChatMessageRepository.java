package org.example.chatservice.mock;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeChatMessageRepository implements ChatMessageRepository {

    private final List<ChatMessage> chatMessageList = Collections.synchronizedList(new ArrayList<>());
    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessageList.add(chatMessage);
        return chatMessage;
    }

    @Override
    public Optional<List<ChatMessage>> findAllByRoomId(String roomId) {
        List<ChatMessage> chatMessages = chatMessageList.stream()
                .filter(chatMessage -> roomId.equals(chatMessage.getRoomId()))
                .collect(Collectors.toList());

        return Optional.of(chatMessages);
    }
}
