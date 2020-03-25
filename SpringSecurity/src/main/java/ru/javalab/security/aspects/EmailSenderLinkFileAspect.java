package ru.javalab.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.security.models.Mail;
import ru.javalab.security.models.User;
import ru.javalab.security.services.EmailService;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class EmailSenderLinkFileAspect {

   @Autowired
   private EmailService emailService;

    @After(value = "execution(* ru.javalab.security.repositories.UsersRepositoryImpl.save(*))")
    public void sendConfirmation(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        String email = user.getEmail();
        String link = user.getConfirmLink();

        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("link", link);

        Mail mail = Mail.builder()
                .to(email)
                .from("no-reply@gmail.com")
                .subject("Confirm email")
                .model(model)
                .build();

        emailService.sendMessage(mail);
    }
}