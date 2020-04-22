package ru.javalab.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.chat.dto.*;
import ru.javalab.chat.models.Chat;
import ru.javalab.chat.models.User;
import ru.javalab.chat.repositories.ChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public ResponseInviteChatDto inviteChat(InviteChatDto inviteChatDto) {
        Optional<Chat> optionalChat = chatRepository.findById(inviteChatDto.getId());
        if (optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if (chat.getPassword().equals(inviteChatDto.getPassword())){
                return ResponseInviteChatDto.builder()
                        .id(chat.getId())
                        .name(chat.getName())
                        .messages(MessageDto.from(chat.getMessages()))
                        .build();
            } else throw new IllegalArgumentException("Wrong password");
        } else throw new IllegalArgumentException("Chat did not find");
    }

    @Override
    public void createChat(CreateChatDto createChatDto) {
        chatRepository.save(Chat.builder()
                .name(createChatDto.getName())
                .password(createChatDto.getPassword())
                .creator(User.builder()
                        .id(createChatDto.getUserId())
                        .build())
                .build());
    }

    @Override
    public List<ChatDto> getAllChats() {
        List<ChatDto> chatDtoList = new ArrayList<>();
        chatRepository.findAll().forEach(chat -> chatDtoList.add(ChatDto.from(chat)));
        return chatDtoList;
    }

    @Override
    public List<ChatDto> getChatsFotUser(Long userId) {
        return ChatDto.from(chatRepository.findByCreator(User.builder().id(userId).build()));
    }
}
