package com.example.auth.util.account;

import com.example.auth.config.MailConfig;
import com.example.auth.mapper.PasswordMapper;
import com.example.auth.util.code.VerifyCodeUtil;
import com.example.auth.util.mail.IMessageBuilder;
import com.example.auth.util.mail.MessageBuilderFactory;
import com.example.common.core.exception.BusinessException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordReset {
    
    private final PasswordMapper passwordMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerifyCodeUtil verifyCodeUtil;
    private final MailConfig mailConfig;

    // 重置后的默认密码
    private static final String DEFAULT_PASSWORD = "123456@ABC";

    /**
     * 重置密码为默认密码并发送邮件通知
     * @param account 用户账号
     * @param email 用户邮箱
     * @return 影响行数
     */
    public int resetToDefault(String account, String email) {
        int rows = resetToDefault(account);
        
        if (rows > 0 && email != null) {
            try {
                // 构建并发送密码重置通知邮件
                IMessageBuilder messageBuilder = MessageBuilderFactory.createPasswordResetNotifyBuilder(
                    mailConfig, 
                    email, 
                    DEFAULT_PASSWORD
                );
                Message message = messageBuilder.build();
                Transport.send(message);
                log.info("密码重置通知邮件发送成功: {}", email);
            } catch (MessagingException e) {
                log.error("密码重置通知邮件发送失败: {}", email, e);
                // 邮件发送失败不影响密码重置结果
            }
        }
        
        return rows;
    }

    /**
     * 通过验证码重置密码并发送邮件通知
     * @param account 账号
     * @param code 验证码
     * @param email 邮箱
     * @return 影响行数
     */
    public int resetWithCode(String account, String code, String email) {
        int rows = resetWithCode(account, code);
        
        if (rows > 0 && email != null) {
            try {
                // 构建并发送密码重置通知邮件
                IMessageBuilder messageBuilder = MessageBuilderFactory.createPasswordResetNotifyBuilder(
                    mailConfig, 
                    email, 
                    DEFAULT_PASSWORD
                );
                Message message = messageBuilder.build();
                Transport.send(message);
                log.info("密码重置通知邮件发送成功: {}", email);
            } catch (MessagingException e) {
                log.error("密码重置通知邮件发送失败: {}", email, e);
            }
        }
        
        return rows;
    }

    /**
     * 重置密码为默认密码
     * @param account 用户账号
     * @return 影响行数
     */
    public int resetToDefault(String account) {
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        int rows = passwordMapper.updatePassword(account, encodedPassword);
        
        if (rows > 0) {
            log.info("重置密码成功: {}", account);
        } else {
            log.error("重置密码失败: {}", account);
        }
        
        return rows;
    }

    /**
     * 通过验证码重置密码
     * @param account 账号
     * @param code 验证码
     * @return 影响行数
     */
    public int resetWithCode(String account, String code) {
        // 验证验证码
        if (!verifyCodeUtil.verifyCode(account, code)) {
            throw new BusinessException("验证码无效");
        }
        
        int rows = resetToDefault(account);
        
        if (rows > 0) {
            // 删除验证码
            verifyCodeUtil.deleteCode(account);
        }
        
        return rows;
    }

    /**
     * 修改密码
     * @param account 账号
     * @param newPassword 新密码
     * @return 影响行数
     */
    public int changePassword(String account, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        int rows = passwordMapper.updatePassword(account, encodedPassword);
        
        if (rows > 0) {
            log.info("修改密码成功: {}", account);
        } else {
            log.error("修改密码失败: {}", account);
        }
        
        return rows;
    }

    /**
     * 获取默认密码
     */
    public String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }
}
