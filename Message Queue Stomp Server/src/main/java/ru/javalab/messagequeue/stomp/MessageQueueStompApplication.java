package ru.javalab.messagequeue.stomp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@SpringBootApplication
public class MessageQueueStompApplication {

    @Bean
    public Map<String, Queue<String>> queues() {
        return new HashMap<>();
    }

    @Bean
    public Map<String, WebSocketSession> webSocketSessionMap() {
        return new HashMap<>();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageQueueStompApplication.class, args);
    }

}
