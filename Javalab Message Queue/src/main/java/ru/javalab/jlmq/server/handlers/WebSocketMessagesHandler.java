package ru.javalab.jlmq.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.javalab.jlmq.server.dispatcher.RequestDispatcher;
import ru.javalab.jlmq.server.protocol.JsonMessage;



@Component
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    @Autowired
    RequestDispatcher dispatcher;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload().toString());
        JsonMessage jsonMessage = objectMapper.readValue(message.getPayload().toString(), JsonMessage.class);
        if (!dispatcher.doDispatch(session, jsonMessage)) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    JsonMessage.builder()
                            .header("ERROR")
                            .payload("Error").build())));
        }
    }
}