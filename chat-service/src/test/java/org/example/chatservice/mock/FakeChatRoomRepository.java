package org.example.chatservice.mock;

import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;

import java.util.*;

public class FakeChatRoomRepository implements ChatRoomRepository {

    private final List<ChatRoom> chatRooms = Collections.synchronizedList(new ArrayList<>());
    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        chatRooms.removeIf(item -> Objects.equals(chatRoom.getId(),item.getId()));
        chatRooms.add(chatRoom);
        return chatRoom;
    }

    @Override
    public Optional<ChatRoom> findById(String id) {
        return chatRooms.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Optional<List<ChatRoom>> findAll() {
        return chatRooms.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(chatRooms));
    }

    @Override
    public void deleteById(String id) {
        chatRooms.removeIf(item -> item.getId().equals(id));
    }
}
