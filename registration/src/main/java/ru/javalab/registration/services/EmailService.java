package ru.javalab.registration.services;

import ru.javalab.registration.models.User;

public interface EmailService {
    User sendMessage(User user);
}
