package ru.javalab.messagequeueclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import ru.javalab.messagequeueclient.protocol.JsonMessage;
import ru.javalab.messagequeueclient.protocol.handler.WebSocketHandlerImpl;
import ru.javalab.messagequeueclient.protocol.payload.Create;
import ru.javalab.messagequeueclient.protocol.payload.Receive;
import ru.javalab.messagequeueclient.protocol.payload.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.function.Consumer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JavalabMessageQueueConsumer<T extends Serializable> {

    private static String address = "ws://localhost/jlmq";
    private WebSocketHandlerImpl<T> handler;
    private String queue;
    private WebSocketClient socketClient;
    private WebSocketSession socketSession;

    @Autowired
    private ObjectMapper mapper;

    private Consumer<T> fail;
    private Consumer<T> success;
    private Class<T> tClass;

    JavalabMessageQueueConsumer handledClass(Class<T> tClass) {
        this.tClass = tClass;
        return this;
    }

    JavalabMessageQueueConsumer onSuccess(Consumer consumer) {
        this.success = consumer;
        return this;
    }

    JavalabMessageQueueConsumer onFail(Consumer consumer) {
        this.fail = consumer;
        return this;
    }

    @SneakyThrows
    JavalabMessageQueueConsumer connect() {
        this.mapper = new ObjectMapper();
        this.socketClient = new StandardWebSocketClient();
        handler = new WebSocketHandlerImpl<>(fail ,success, tClass);
        socketSession = socketClient.doHandshake(handler, new WebSocketHttpHeaders(), URI.create(address)).get();
        return this;
    }


    public void create(String name) {
        queue = name;
        try {
            socketSession.sendMessage(new TextMessage(
                    mapper.writeValueAsString(
                            JsonMessage
                                    .builder()
                                    .header("CREATE")
                                    .payload(mapper.writeValueAsString(Create
                                            .builder()
                                            .queueName(name)
                                            .build()))
                                    .build())));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void subscribe(String name) {
        queue = name;
        try {
            socketSession.sendMessage(new TextMessage(
                    mapper.writeValueAsString(
                            JsonMessage
                                    .builder()
                                    .header("SUBSCRIBE")
                                    .payload(mapper.writeValueAsString(Subscribe
                                            .builder()
                                            .queueName(name)
                                            .build()))
                                    .build())));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void add(T t) {
        try {
            socketSession.sendMessage(new TextMessage(
                    mapper.writeValueAsString(
                            JsonMessage
                                    .builder()
                                    .header("RECEIVE")
                                    .payload(mapper.writeValueAsString(Receive
                                            .builder()
                                            .content(mapper.writeValueAsString(t))
                                            .queueName(queue)
                                            .build()))
                                    .build())));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}