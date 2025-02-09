package com.example.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailConfig {
    private String host;
    private int port;
    private String username;
    private String password;

    @Bean
    public Properties mailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return props;
    }
}
