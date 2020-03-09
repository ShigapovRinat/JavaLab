package ru.javalab.downloadingFiles.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.javalab.downloadingFiles.models.FileInfo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
public class EmailSenderLinkFileAspect {

    @Autowired
    private JavaMailSender sender;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @After(value = "execution(* ru.javalab.downloadingFiles.services.FilesInfoService.save(*,*))")
    public void sendConfirmation(JoinPoint joinPoint) {
        System.out.println("mail");
        FileInfo fileInfo = (FileInfo) joinPoint.getArgs()[0];
        String email = fileInfo.getEmail();
        String originalName = fileInfo.getOriginalFileName();
        String storageName = fileInfo.getStorageFileName();

        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }

        String mailText = "You can download file " + originalName + " <a href='localhost:8080/files/" + storageName + "'>Link</a>";

//        executorService.submit(() -> {
//            MimeMessage message = sender.createMimeMessage();
//            try {
//                message.setContent(mailText, "text/html");
//                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
//                messageHelper.setTo(email);
//                messageHelper.setSubject("Cсылка на скачивание файла");
//                messageHelper.setText(mailText, true);
//                messageHelper.setFrom("noreply");
//            } catch (MessagingException e) {
//                throw new IllegalArgumentException(e);
//            }
//            this.sender.send(message);
//        });
        executorService.submit(() -> {
            MimeMessage message = sender.createMimeMessage();
            try {
                message.setContent(mailText, "text/html");
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
                messageHelper.setTo(email);
                messageHelper.setSubject("Подтерждение регистрации");
                messageHelper.setText(mailText, true);
                messageHelper.setFrom("noreply");
            } catch (MessagingException e) {
                throw new IllegalArgumentException(e);
            }
            this.sender.send(message);
        });
    }
}