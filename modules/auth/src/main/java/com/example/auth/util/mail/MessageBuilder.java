package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MessageBuilder implements IMessageBuilder {

    private final MailConfig mailConfig;
    private final SendVerifyCodeDTO dto;
    private final int type;
    private Session session;

    public MessageBuilder(MailConfig mailConfig, SendVerifyCodeDTO dto, int type) {
        this.mailConfig = mailConfig;
        this.dto = dto;
        this.type = type;
        this.session = createSession();
    }

    private Session createSession() {
        return Session.getInstance(mailConfig.mailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });
    }

    @Override
    public Message build() throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailConfig.getUsername()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dto.getTarget()));

        switch (type) {
            case MessageBuilderFactory.TYPE_REGISTER:
                message.setSubject(EmailContentBuilder.buildRegisterSubject(dto.getCode()));
                message.setText(EmailContentBuilder.buildRegisterContent(dto.getCode()));
                break;
            case MessageBuilderFactory.TYPE_RESET_PASSWORD:
                message.setSubject(EmailContentBuilder.buildResetPasswordSubject(dto.getCode()));
                message.setText(EmailContentBuilder.buildResetPasswordContent(dto.getCode()));
                break;
            default:
                throw new IllegalArgumentException("Unknown message type: " + type);
        }

        return message;
    }
}
