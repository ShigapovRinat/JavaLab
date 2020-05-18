package ru.javalab.messagequeueclient.protocol.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import ru.javalab.messagequeueclient.protocol.JsonMessage;
import ru.javalab.messagequeueclient.protocol.Message;
import ru.javalab.messagequeueclient.protocol.payload.Completed;

import java.util.function.Consumer;

public class WebSocketHandlerImpl<T> implements WebSocketHandler {

    @Autowired
    private ObjectMapper mapper;

    private Consumer<T> fail;
    private Consumer<T> success;
    private Class<T> tClass;

    public WebSocketHandlerImpl(Consumer<T> fail, Consumer<T> success, Class<T> aClass) {
        this.fail = fail;
        this.success = success;
        tClass = aClass;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println(webSocketMessage.getPayload());
        JsonMessage jsonMessage = mapper.readValue(webSocketMessage.getPayload().toString(), JsonMessage.class);
        String header = jsonMessage.getHeader();
        if (header.equals("ERROR")) {
            System.out.println(jsonMessage.getPayload());
        } else if (header.equals("SEND")) {
            Message message =
                    mapper.readValue(jsonMessage.getPayload(), Message.class);
            success.accept(mapper.readValue(message.getContent(), tClass));
            webSocketSession.sendMessage(
                    new TextMessage(
                            mapper.writeValueAsString(
                                    JsonMessage
                                            .builder()
                                            .header("COMPLETED")
                                            .payload(mapper.writeValueAsString(Completed
                                                    .builder()
                                                    .messageId(message.getMessageId())
                                                    .build()))
                                            .build())));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}