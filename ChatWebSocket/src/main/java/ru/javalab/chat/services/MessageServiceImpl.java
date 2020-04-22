package ru.javalab.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.chat.dto.MessageDto;
import ru.javalab.chat.models.Chat;
import ru.javalab.chat.models.Message;
import ru.javalab.chat.models.User;
import ru.javalab.chat.repositories.MessageRepository;

import java.time.LocalDateTime;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void save(MessageDto messageDto) {
        messageRepository.save(Message.builder().text(messageDto.getText())
                .chat(Chat.builder()
                        .id(messageDto.getChat_id())
                        .build())
                .creator(User.builder()
                        .id(messageDto.getUser_id())
                        .build())
                .createAt(LocalDateTime.now())
                .build());
    }
}
