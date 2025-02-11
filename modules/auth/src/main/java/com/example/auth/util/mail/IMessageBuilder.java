package com.example.auth.util.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;

public interface IMessageBuilder {
    Message build() throws MessagingException;
} 