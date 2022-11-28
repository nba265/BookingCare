package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public void sendMimeMessage(EmailDTO emailDTO, Context ctx, String template) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(String.join(",", emailDTO.getRecipients()));
        helper.setSubject(emailDTO.getSubject());

        String htmlContent = htmlTemplateEngine.process(template, ctx);
        helper.setText(htmlContent, true);
        javaMailSender.send(message);
    }
}
