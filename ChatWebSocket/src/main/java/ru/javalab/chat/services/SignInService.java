package ru.javalab.chat.services;

import ru.javalab.chat.dto.SignInDto;
import ru.javalab.chat.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInDto);
}
