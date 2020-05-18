package ru.javalab.jlmq.server.dispatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.javalab.jlmq.server.models.JavaLabMessageQueue;
import ru.javalab.jlmq.server.models.Message;
import ru.javalab.jlmq.server.protocol.JsonMessage;
import ru.javalab.jlmq.server.protocol.payload.Accepted;
import ru.javalab.jlmq.server.protocol.payload.Create;
import ru.javalab.jlmq.server.protocol.payload.Receive;
import ru.javalab.jlmq.server.protocol.payload.Subscribe;

import java.io.IOException;
import java.util.*;


@Component
public class RequestDispatcher {

    @Autowired
    ObjectMapper objectMapper;

    private static List<JavaLabMessageQueue> notSubscribed = new ArrayList<>();
    private static Map<WebSocketSession, JavaLabMessageQueue> subscribed = new HashMap<>();

    public boolean doDispatch(WebSocketSession session, JsonMessage message) {
        try {
            String header = message.getHeader();
            System.out.println(message.getPayload().toString());
            switch (header) {
                case "CREATE":
                    Create create = objectMapper.readValue(message.getPayload().toString(), Create.class);
                    notSubscribed.add(JavaLabMessageQueue
                            .builder()
                            .name(create.getQueueName())
                            .deque(new ArrayDeque<>())
                            .build());
                    break;
                case "SUBSCRIBE":
                    Subscribe subscribe = objectMapper.readValue(message.getPayload().toString(), Subscribe.class);
                    for (int i = 0; i < notSubscribed.size(); i++) {
                        if (notSubscribed.get(i).getName().equals(subscribe.getQueueName())) {
                            subscribed.put(session, notSubscribed.get(i));
                            notSubscribed.remove(i);
                            sendNext(session);
                            return true;
                        }
                    }
                    System.out.println("Can`t find queue with such name");
                    return false;
                case "ACCEPTED":
                    Accepted accepted = objectMapper.readValue(message.getPayload().toString(), Accepted.class);
                    subscribed.get(session).getDeque().getFirst().setStatus("ACCEPTED");
                    return true;
                case "COMPLETED":
                    subscribed.get(session).getDeque().pop().setStatus("COMPLETED");
                    sendNext(session);
                    return true;
                case "FAILED":
                    subscribed.get(session).getDeque().getFirst().setStatus("FAILED");
                    return true;
                case "RECEIVE":
                    Receive receive = objectMapper.readValue(message.getPayload().toString(), Receive.class);
                    Message toAdd = Message.builder()
                            .content(receive.getContent())
                            .messageId(UUID.randomUUID().toString())
                            .build();

                    if (subscribed.get(session) != null && subscribed.get(session).getName().equals(receive.getQueueName())) {
                        subscribed.get(session).getDeque().addLast(toAdd);
                        return true;
                    }
                    for (JavaLabMessageQueue javaLabMessageQueue : subscribed.values()) {
                        if (javaLabMessageQueue.getName().equals(receive.getQueueName())) {
                            javaLabMessageQueue.getDeque().addLast(toAdd);
                            return true;
                        }
                    }
                    for (JavaLabMessageQueue value : subscribed.values()) {
                        if (value.getName().equals(receive.getQueueName())) {
                            value.getDeque().addLast(toAdd);
                            return true;
                        }
                    }
                    return false;
                case "SEND":
                    sendNext(session);
                    return true;
                default:
                    return false;
            }
            return false;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void sendNext(WebSocketSession session) {
        try {
            if (!subscribed.get(session).getDeque().isEmpty()) {
                Message toSend =
                        subscribed.get(session).getDeque().getFirst();
                JsonMessage jsonMessage = JsonMessage.builder()
                        .header("SEND")
                        .payload(objectMapper.writeValueAsString(toSend))
                        .build();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(jsonMessage)));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}