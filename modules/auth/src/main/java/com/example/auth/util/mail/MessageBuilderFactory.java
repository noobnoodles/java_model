package com.example.auth.util.mail;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;

public class MessageBuilderFactory {
    
    public static final int TYPE_REGISTER = 1;
    public static final int TYPE_RESET_PASSWORD = 2;
    
    public static IMessageBuilder createBuilder(int type, MailConfig mailConfig, SendVerifyCodeDTO dto) {
        return new MessageBuilder(mailConfig, dto, type);
    }
} 