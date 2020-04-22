package ru.javalab.chat.services;

import ru.javalab.chat.dto.ChatDto;
import ru.javalab.chat.dto.CreateChatDto;
import ru.javalab.chat.dto.InviteChatDto;
import ru.javalab.chat.dto.ResponseInviteChatDto;

import java.util.List;

public interface ChatService {

    ResponseInviteChatDto inviteChat(InviteChatDto inviteChatDto);

    void createChat(CreateChatDto createChatDto);

    List<ChatDto> getAllChats();

    List<ChatDto> getChatsFotUser(Long userId);
}
