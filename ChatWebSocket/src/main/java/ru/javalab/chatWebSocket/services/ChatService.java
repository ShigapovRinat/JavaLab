package ru.javalab.chatWebSocket.services;

import ru.javalab.chatWebSocket.dto.ChatDto;
import ru.javalab.chatWebSocket.dto.CreateChatDto;
import ru.javalab.chatWebSocket.dto.InviteChatDto;
import ru.javalab.chatWebSocket.dto.ResponseInviteChatDto;

import java.util.List;

public interface ChatService {

    ResponseInviteChatDto inviteChat(InviteChatDto inviteChatDto);

    void createChat(CreateChatDto createChatDto);

    List<ChatDto> getAllChats();

    List<ChatDto> getChatsFotUser(Long userId);
}
