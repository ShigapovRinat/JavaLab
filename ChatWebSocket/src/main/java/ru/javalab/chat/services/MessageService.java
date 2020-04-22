package ru.javalab.chat.services;

import ru.javalab.chat.dto.MessageDto;

public interface MessageService {
    void save(MessageDto messageDto);
}
