package ru.javalab.chatWebSocket.services;

import ru.javalab.chatWebSocket.dto.MessageDto;

public interface MessageService {
    void save(MessageDto messageDto);
}
