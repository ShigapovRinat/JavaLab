package ru.javalab.jlmq.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.javalab.jlmq.server.models.Message;
import ru.javalab.jlmq.server.repositories.MessageRepository;

@Repository
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

}