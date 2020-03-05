package ru.javalab.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.javalab.registration.models.User;
import ru.javalab.registration.repositories.UsersRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public User sendMessage(User user) {
        String confirmLink = UUID.randomUUID().toString();
        user.setConfirmLink(confirmLink);
        String mailText = "<a href='http://localhost:8080/confirm/" + confirmLink + "'>Link</a>";

        executorService.submit(() -> {
            MimeMessage message = sender.createMimeMessage();
            try {
                message.setContent(mailText, "text/html");
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Подтерждение регистрации");
                messageHelper.setText(mailText, true);
                messageHelper.setFrom("noreply");
            } catch (MessagingException e) {
                throw new IllegalArgumentException(e);
            }
            this.sender.send(message);
        });
        return user;
    }
}
