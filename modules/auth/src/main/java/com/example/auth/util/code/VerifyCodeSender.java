package com.example.auth.util.code;

import com.example.auth.config.MailConfig;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import com.example.auth.util.mail.IMessageBuilder;
import com.example.auth.util.mail.MessageBuilderFactory;
import com.example.common.core.exception.BusinessException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyCodeSender {
    
    private final VerifyCodeUtil verifyCodeUtil;
    private final MailConfig mailConfig;
    
    /**
     * 发送验证码
     * @param sendVerifyCodeDTO 发送验证码参数
     * @return 发送结果(1:成功,0:失败)
     */
    public int sendCode(SendVerifyCodeDTO sendVerifyCodeDTO) {
        // 检查是否可以发送验证码
        if (!verifyCodeUtil.canSendCode(sendVerifyCodeDTO.getAccount())) {
            throw new BusinessException("验证码发送过于频繁，请稍后再试");
        }
        
        // 生成验证码
        String code = verifyCodeUtil.generateCode();
        sendVerifyCodeDTO.setCode(code);
        
        // 发送邮件验证码
        boolean sendResult = sendEmailCode(sendVerifyCodeDTO);
        
        if (sendResult) {
            // 保存验证码
            verifyCodeUtil.saveCode(sendVerifyCodeDTO.getAccount(), code);
            log.info("[验证码] 用户:{} 发送验证码成功 目标:{}", 
                    sendVerifyCodeDTO.getAccount(), sendVerifyCodeDTO.getTarget());
            return 1;
        } else {
            log.error("[验证码] 用户:{} 发送验证码失败 目标:{}", 
                    sendVerifyCodeDTO.getAccount(), sendVerifyCodeDTO.getTarget());
            return 0;
        }
    }
    
    /**
     * 发送邮件验证码
     */
    private boolean sendEmailCode(SendVerifyCodeDTO dto) {
        try {
            // 根据不同场景使用不同的邮件构建器
            IMessageBuilder messageBuilder = MessageBuilderFactory.createBuilder(
                dto.getTargetType(),  // 使用targetType来区分不同场景
                mailConfig, 
                dto
            );
            Message message = messageBuilder.build();
            jakarta.mail.Transport.send(message);
            return true;
        } catch (MessagingException e) {
            log.error("[邮件验证码] 发送失败", e);
            return false;
        }
    }
} 