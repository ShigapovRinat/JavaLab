package ru.javalab.chat.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.javalab.chat.dto.MessageDto;
import ru.javalab.chat.services.MessageService;

import java.util.*;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();
    private static final Map<Long, Set<String>> rooms = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        MessageDto messageFromJson = objectMapper.readValue(messageText, MessageDto.class);

        if (!rooms.containsKey(messageFromJson.getChat_id())) {
            rooms.put(messageFromJson.getChat_id(), new HashSet<>());
        }

        if (!sessions.containsKey(messageFromJson.getUser_login())) {
            sessions.put(messageFromJson.getUser_login(), session);
        }

        Set<String> usersChat = rooms.get(messageFromJson.getChat_id());
        usersChat.add(messageFromJson.getUser_login());
        rooms.put(messageFromJson.getChat_id(), usersChat);

        messageService.save(messageFromJson);

        for (String from : rooms.get(messageFromJson.getChat_id())) {
            try {
                WebSocketSession currentSession = sessions.get(from);
                currentSession.sendMessage(message);
            } catch (Exception ex) {
                synchronized (sessions) {
                    sessions.remove(messageFromJson.getUser_login());
                    sessions.put(messageFromJson.getUser_login(), session);
                    session.sendMessage(message);
                }
            }
        }
    }
}