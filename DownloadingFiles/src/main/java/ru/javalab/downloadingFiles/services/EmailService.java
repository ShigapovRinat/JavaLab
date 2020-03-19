package ru.javalab.downloadingFiles.services;

import ru.javalab.downloadingFiles.models.Mail;

public interface EmailService {
    void sendMessage(Mail mail);
}
