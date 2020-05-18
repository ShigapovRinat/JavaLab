package ru.javalab.messagequeue.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;

@Component
public class MessageManager {

    @Autowired
    private Map<String, Queue<String>> queues;

    @Autowired
    private SimpMessagingTemplate template;

    public void sendTask(String queueName) {
        if (queues.containsKey(queueName) && !queues.get(queueName).isEmpty()) {
            template.convertAndSend("/jlmq/queue/" + queueName, queues.get(queueName).peek());
        }
    }
}