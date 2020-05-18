package ru.javalab.messagequeue.stomp.protocol;

import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;

@NoArgsConstructor
public class JlmqProducer {
    private String queueName;
    private StompSession session;

    public JlmqProducer(String queueName, StompSession session) {
        this.queueName = queueName;
        this.session = session;
    }

    public void send(Object payload) {
        session.send("/jlmq/" + queueName, payload);
    }
}