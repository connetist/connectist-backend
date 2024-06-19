package org.example.chatservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    public KafkaProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, ChatMessage chatMessage) throws Exception{

        System.out.println("Kafka producer : " + chatMessage.toString());
        System.out.println("Topic: " + topic);
        String kafkaMessage = objectMapper.writeValueAsString(chatMessage);
        kafkaTemplate.send(topic, String.valueOf(kafkaMessage));
    }
}
