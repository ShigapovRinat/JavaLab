package ru.javalab.security.services;

import ru.javalab.security.models.Mail;

public interface EmailService {
    void sendMessage(Mail mail);
}
