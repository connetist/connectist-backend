package org.example.chatservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(groupId = "chatting-group", topics= "chatting" )
    public void listen(String kafkaMessage) throws Exception{
        System.out.println("CONSUMER IN");
        ChatMessage chatMessage = objectMapper.readValue(kafkaMessage,ChatMessage.class);
        String roomId=  chatMessage.getRoomId();
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" +roomId, chatMessage);

        System.out.println("Kafka Consumer : " + chatMessage + " Room ID : " + roomId);

    }
}
