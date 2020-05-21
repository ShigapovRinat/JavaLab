package ru.javalab.messagequeue.stomp.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomerStompSessionHandler extends StompSessionHandlerAdapter {

    private StompSession session;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Received task");
        System.out.println(payload.toString());
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("destination", "/app/jlmq/complete" + headers.getDestination()
                .substring(headers.getDestination().lastIndexOf("/")));
        stompHeaders.add("type", "complete");
        session.send(stompHeaders, payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connected");
        this.session = session;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        throw new RuntimeException("Failure in WebSocket handling", exception);
    }
}