package ru.javalab.downloadingFiles.services;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import ru.javalab.downloadingFiles.models.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Override
    public void sendMessage(Mail mail) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

//        helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

            Template t = null;

            t = freeMarkerConfig.getConfiguration().getTemplate("email_notification.ftl");

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            sender.send(message);
        } catch (IOException | TemplateException | MessagingException e) {
           throw new IllegalArgumentException(e);
        }
    }


//    private ExecutorService executorService = Executors.newCachedThreadPool();
//
//    @Override
//    public void sendMessage(String mail, String mailText) {
//        executorService.submit(() -> {
//            MimeMessage message = sender.createMimeMessage();
//            try {
//                message.setContent(mailText, "text/html");
//                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
//                messageHelper.setTo(mail);
//                messageHelper.setSubject("Cсылка на скачивание файла");
//                messageHelper.setText(mailText, true);
//                messageHelper.setFrom("noreply");
//            } catch (MessagingException e) {
//                System.out.println(e);
//                throw new IllegalArgumentException(e);
//            }
//            this.sender.send(message);
//        });
//    }
}
