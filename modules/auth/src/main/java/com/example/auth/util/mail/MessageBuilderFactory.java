package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;

public class MessageBuilderFactory {
    
    public static final int TYPE_REGISTER = 1;
    public static final int TYPE_RESET_PASSWORD = 2;
    
    public static IMessageBuilder createBuilder(int type, MailConfig mailConfig, SendVerifyCodeDTO dto) {
        switch (type) {
            case TYPE_REGISTER:
                return new MessageBuilder(mailConfig, dto);
            case TYPE_RESET_PASSWORD:
                return new ResetPasswordMessageBuilder(mailConfig, dto);
            default:
                throw new IllegalArgumentException("Unknown message builder type: " + type);
        }
    }
} 