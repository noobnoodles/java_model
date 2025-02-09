package com.example.auth.util;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MessageBuilder {
    private final MailConfig mailConfig;
    private final SendVerifyCodeDTO dto;
    private Session session;

    public MessageBuilder(MailConfig mailConfig, SendVerifyCodeDTO dto) {
        this.mailConfig = mailConfig;
        this.dto = dto;
        this.session = createSession();
    }

    private Session createSession() {
        return Session.getInstance(mailConfig.mailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });
    }

    public Message build() throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailConfig.getUsername()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dto.getTarget()));
        message.setSubject("验证码");
        message.setText("您的验证码是：" + dto.getCode());
        return message;
    }
}
