package ru.javalab.chatWebSocket.services;

import io.jsonwebtoken.Claims;

public interface TokenService {
    Claims encodeToken(String token);
}
