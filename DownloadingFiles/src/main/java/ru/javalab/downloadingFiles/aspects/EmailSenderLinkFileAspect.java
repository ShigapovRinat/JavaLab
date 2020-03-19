package ru.javalab.downloadingFiles.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.downloadingFiles.models.FileInfo;
import ru.javalab.downloadingFiles.models.Mail;
import ru.javalab.downloadingFiles.services.EmailService;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class EmailSenderLinkFileAspect {

   @Autowired
   private EmailService emailService;

    @After(value = "execution(* ru.javalab.downloadingFiles.services.FilesInfoService.save(*,*))")
    public void sendConfirmation(JoinPoint joinPoint) {
        FileInfo fileInfo = (FileInfo) joinPoint.getArgs()[0];
        String email = fileInfo.getEmail();
        String originalName = fileInfo.getOriginalFileName();
        String storageName = fileInfo.getStorageFileName();

        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("originalName", originalName);
        model.put("storageName", storageName);

        Mail mail = Mail.builder()
                .to(email)
                .from("no-reply@gmail.com")
                .subject("Download file")
                .model(model)
                .build();

        emailService.sendMessage(mail);
    }
}