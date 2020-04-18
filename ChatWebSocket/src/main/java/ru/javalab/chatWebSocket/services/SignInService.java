package ru.javalab.chatWebSocket.services;

import ru.javalab.chatWebSocket.dto.SignInDto;
import ru.javalab.chatWebSocket.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInDto);
}
