package ru.javalab.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.javalab.registration.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EmailServiceImplWithPicture implements EmailService {

    @Autowired
    private JavaMailSender sender;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public User sendMessage(User user) {

        String confirmLink = UUID.randomUUID().toString();
        user.setConfirmLink(confirmLink);
        String mailText = "<h3>Пройдите по ссылке, чтобы подтвердить почту</h3>" +
                "<a href='http://localhost:8080/confirm/" + confirmLink + "'>Link</a><br>" +
                "<img src=\"cid:image1\">" +
                "<h3>It is image</h3>";


        executorService.submit(() -> {
            try {
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Подтерждение регистрации");
                messageHelper.setFrom("noreply");


                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "image1");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                imagePart.attachFile("C:/Users/pc/Desktop/image1.jpg");
                messageHelper.getMimeMultipart().addBodyPart(imagePart);

                messageHelper.setText(mailText, true);
                this.sender.send(message);
            } catch (MessagingException |IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
        return user;

    }
}
