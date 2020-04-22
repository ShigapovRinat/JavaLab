package ru.javalab.chat.services;

import io.jsonwebtoken.Claims;

public interface TokenService {
    Claims encodeToken(String token);
}
