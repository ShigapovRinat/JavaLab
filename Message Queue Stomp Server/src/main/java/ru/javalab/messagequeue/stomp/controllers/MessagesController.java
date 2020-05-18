package ru.javalab.messagequeue.stomp.controllers;

import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.javalab.messagequeue.stomp.MessageManager;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

@Controller
public class MessagesController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Map<String, Queue<String>> queues;

    @Autowired
    private MessageManager manager;


    @MessageMapping("/produce/{queue}")
    public void receiveMessageFromProducer(Message<String> message, @DestinationVariable String queue) {
        System.out.println("Creating task");
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (!queues.containsKey(queue)) {
            queues.put(queue, new ArrayDeque<>());
        }
        queues.get(queue).add(message.getPayload());
        manager.sendTask(queue);
    }

    @MessageMapping("/jlmq/queue/{queue}")
    public void receiveMessageFromClient(Message<String> message, @DestinationVariable String queue) {
        System.out.println(message.toString());
    }

    @MessageMapping("/jlmq/complete/{queue}")
    public void receiveMessageComplete(Message<String> message, @DestinationVariable String queue) {
        queues.get(queue).poll();
        manager.sendTask(queue);
        template.convertAndSend("/jlmq/complete/" + queue, message.getPayload());
    }
}