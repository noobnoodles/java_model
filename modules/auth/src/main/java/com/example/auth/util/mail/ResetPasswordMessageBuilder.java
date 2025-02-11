package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResetPasswordMessageBuilder implements IMessageBuilder {
    private final MailConfig mailConfig;
    private final SendVerifyCodeDTO dto;
    private Session session;

    public ResetPasswordMessageBuilder(MailConfig mailConfig, SendVerifyCodeDTO dto) {
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
        message.setSubject("安全验证：找回密码验证码 " + dto.getCode() + "（有效期5分钟）");
        
        // 构建邮件内容
        String emailContent = buildEmailContent(dto.getCode());
        message.setText(emailContent);
        
        return message;
    }
    
    private String buildEmailContent(String code) {
        StringBuilder content = new StringBuilder();
        content.append("尊敬的用户：\n\n");
        content.append("您正在申请重置账户密码，请确认是本人操作后使用以下验证码：\n");
        content.append("验证码：").append(code).append("\n");
        content.append("（该验证码5分钟内有效，过期需重新申请）\n\n");
        content.append("⚠️ 安全提醒：\n\n");
        content.append("请勿将验证码告知他人，平台工作人员不会主动索取\n\n");
        content.append("如未发起密码重置请求，请立即修改账户密码\n\n");
        content.append("感谢您对我们的支持！\n\n\n");
        
        // 添加日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        content.append(formatter.format(now));
        
        return content.toString();
    }
} 