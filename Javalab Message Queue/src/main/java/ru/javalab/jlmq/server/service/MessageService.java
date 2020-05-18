package ru.javalab.jlmq.server.service;

import ru.javalab.jlmq.server.models.Message;

public interface MessageService {
    void save(Message task);
}