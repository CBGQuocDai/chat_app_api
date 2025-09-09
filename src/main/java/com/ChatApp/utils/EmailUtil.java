package com.ChatApp.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(String target, String subject, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(message, true);
            Context ctx =new Context();
            ctx.setVariable("otp", otp);
            mail.setTo(target);
            mail.setSubject(subject);
            mail.setText(templateEngine.process("email.html", ctx), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
