package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;

public class MessageBuilderFactory {
    
    public static final int TYPE_REGISTER = 1;
    public static final int TYPE_RESET_PASSWORD = 2;
    public static final int TYPE_PASSWORD_RESET_NOTIFY = 3;
    
    public static IMessageBuilder createBuilder(int type, MailConfig mailConfig, SendVerifyCodeDTO dto) {
        return new MessageBuilder(mailConfig, dto, type);
    }

    public static IMessageBuilder createPasswordResetNotifyBuilder(MailConfig mailConfig, String email, String defaultPassword) {
        SendVerifyCodeDTO dto = new SendVerifyCodeDTO();
        dto.setTarget(email);
        dto.setCode(defaultPassword);
        return new MessageBuilder(mailConfig, dto, TYPE_PASSWORD_RESET_NOTIFY);
    }
} 