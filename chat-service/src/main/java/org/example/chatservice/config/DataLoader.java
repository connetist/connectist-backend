package org.example.chatservice.config;

import lombok.Builder;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.example.chatservice.chatMessage.infrastructure.repository.ChatMessageRepository;
import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, MongoTemplate mongoTemplate) {
        return args -> {
            mongoTemplate.dropCollection(ChatRoomEntity.class);
            mongoTemplate.dropCollection(ChatMessageEntity.class);

            ChatMember member1 = ChatMember.builder()
                    .id("id1")
                    .userId("userId1")
                    .createdAt(10000)
                    .lastMessageIdx(0)
                    .build();

            ChatMember member2 = ChatMember.builder()
                    .id("id2")
                    .userId("userId2")
                    .createdAt(10000)
                    .lastMessageIdx(0)
                    .build();

            List<ChatMember> chatMemberList = new ArrayList<>();
            chatMemberList.add(member1);
            chatMemberList.add(member2);
           ChatRoom chatRoom = ChatRoom.builder()
                   .id("roomId")
                   .title("testTitle")
                   .admin(member1)
                   .deparature("testDeparture")
                   .destination("testDestination")
                   .timeTaken(2000)
                   .startTime(1000)
                   .fee(2000)
                   .createdAt(1000)
                   .chatMembers(chatMemberList)
                   .build();
            chatRoomRepository.save(chatRoom);

            ChatMessage chatMessage = ChatMessage.builder()
                    .id("id1")
                    .userId("userId1")
                    .roomId("roomId")
                    .content("chat content")
                    .createdAt(1000)
                    .build();

            chatMessageRepository.save(chatMessage);
        };
    }
}
