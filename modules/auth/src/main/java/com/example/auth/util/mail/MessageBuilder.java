package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageBuilder implements IMessageBuilder {
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
        message.setSubject("欢迎注册！您的验证码是 " + dto.getCode() + "（有效期5分钟）");
        
        // 构建邮件内容
        String emailContent = buildEmailContent(dto.getCode());
        message.setText(emailContent);
        
        return message;
    }
    
    private String buildEmailContent(String code) {
        StringBuilder content = new StringBuilder();
        content.append("尊敬的用户：\n\n");
        content.append("您好！欢迎注册[统一认证中心]，请查收您的账户验证码：\n");
        content.append("验证码：").append(code).append("\n");
        content.append("（该验证码5分钟内有效，仅限本次注册使用）\n\n");
        content.append("为保证账户安全，请勿向任何人泄露此验证码。如非本人操作，请忽略本邮件。\n\n");
        content.append("感谢您的信任，欢迎使用本产品！\n\n\n");
        
        // 添加日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        content.append(formatter.format(now));
        
        return content.toString();
    }
}
