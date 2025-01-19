package com.example.auth.util;

import com.example.auth.model.dto.SendVerifyCodeDTO;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyCodeSender {
    
    private final VerifyCodeUtil verifyCodeUtil;
    
    /**
     * 发送验证码
     * @param sendVerifyCodeDTO 发送验证码参数
     * @return 发送结果(1:成功,0:失败)
     */
    public int sendCode(SendVerifyCodeDTO sendVerifyCodeDTO) {
        // 检查是否可以发送验证码
        if (!verifyCodeUtil.canSendCode(sendVerifyCodeDTO.getSysBelone(), sendVerifyCodeDTO.getAccount())) {
            throw new BusinessException("验证码发送过于频繁，请稍后再试");
        }
        
        // 生成验证码
        String code = verifyCodeUtil.generateCode();
        
        // 根据目标类型发送验证码
        boolean sendResult = switch (sendVerifyCodeDTO.getTargetType()) {
            case 1 -> sendSmsCode(sendVerifyCodeDTO.getTarget(), code);  // 发送短信验证码
            case 2 -> sendEmailCode(sendVerifyCodeDTO.getTarget(), code);  // 发送邮件验证码
            default -> throw new BusinessException("不支持的发送类型");
        };
        
        if (sendResult) {
            // 保存验证码
            verifyCodeUtil.saveCode(sendVerifyCodeDTO.getSysBelone(), sendVerifyCodeDTO.getAccount(), code);
            log.info("[验证码] 用户:{} 系统:{} 发送验证码成功 目标:{}", 
                    sendVerifyCodeDTO.getAccount(), sendVerifyCodeDTO.getSysBelone(), sendVerifyCodeDTO.getTarget());
            return 1;
        } else {
            log.error("[验证码] 用户:{} 系统:{} 发送验证码失败 目标:{}", 
                    sendVerifyCodeDTO.getAccount(), sendVerifyCodeDTO.getSysBelone(), sendVerifyCodeDTO.getTarget());
            return 0;
        }
    }
    
    /**
     * 发送短信验证码
     * TODO: 实现短信发送逻辑
     */
    private boolean sendSmsCode(String phone, String code) {
        log.info("[短信验证码] 发送到:{} 验证码:{}", phone, code);
        return true;
    }
    
    /**
     * 发送邮件验证码
     * TODO: 实现邮件发送逻辑
     */
    private boolean sendEmailCode(String email, String code) {
        log.info("[邮件验证码] 发送到:{} 验证码:{}", email, code);
        return true;
    }
} 