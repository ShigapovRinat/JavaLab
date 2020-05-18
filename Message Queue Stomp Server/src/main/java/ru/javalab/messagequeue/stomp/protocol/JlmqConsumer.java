package ru.javalab.messagequeue.stomp.protocol;

import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;

@NoArgsConstructor
public class JlmqConsumer {
    private StompSession session;

    public JlmqConsumer(StompSession session) {
        this.session = session;
    }
}