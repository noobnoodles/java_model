package com.example.auth.service.impl;

import com.example.auth.mapper.PasswordMapper;
import com.example.auth.model.dto.ForgetPasswordDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import com.example.auth.service.PasswordService;
import com.example.auth.util.code.VerifyCodeSender;
import com.example.auth.util.code.VerifyCodeUtil;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordMapper passwordMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerifyCodeUtil verifyCodeUtil;
    private final VerifyCodeSender verifyCodeSender;
    // 重置后的默认密码
    private static final String DEFAULT_PASSWORD = "123456@ABC";
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        // 验证验证码
        if (!verifyResetCode(forgetPasswordDTO.getTarget(), forgetPasswordDTO.getCode())) {
            throw new BusinessException("验证码无效");
        }
        
        // 更新为默认密码
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        int rows = passwordMapper.updatePasswordByEmail(
            forgetPasswordDTO.getTarget(),
            encodedPassword
        );
        
        if (rows > 0) {
            log.info("重置密码成功: {}", forgetPasswordDTO.getTarget());
            // 删除验证码
            verifyCodeUtil.deleteCode(forgetPasswordDTO.getTarget());
        } else {
            log.error("[密码重置] 用户:{} 系统:{} 重置密码失败", 
                    forgetPasswordDTO.getTarget());
        }
        
        return rows;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changePassword(ForgetPasswordDTO forgetPasswordDTO) {
        // 验证两次密码是否一致
        if (!forgetPasswordDTO.getNewPassword().equals(forgetPasswordDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }
        
        // 验证验证码
        if (!verifyResetCode(forgetPasswordDTO.getTarget(), forgetPasswordDTO.getCode())) {
            throw new BusinessException("验证码无效");
        }
        
        // 更新密码
        String encodedPassword = passwordEncoder.encode(forgetPasswordDTO.getNewPassword());
        int rows = passwordMapper.updatePasswordByEmail(
            forgetPasswordDTO.getTarget(),  // 使用target（邮箱）更新密码
            encodedPassword
        );
        
        if (rows > 0) {
            log.info("[密码修改] 用户:{} 修改密码成功", 
                forgetPasswordDTO.getTarget()
            );
            // 删除验证码
            verifyCodeUtil.deleteCode(forgetPasswordDTO.getTarget());
        } else {
            log.error("[密码修改] 用户:{} 系统:{} 修改密码失败", 
                forgetPasswordDTO.getTarget()
            );
        }
        
        return rows;
    }
    
    @Override
    public boolean verifyResetCode(String account, String code) {
        return verifyCodeUtil.verifyCode(account, code);
    }
    
    @Override
    public int sendVerifyCode(SendVerifyCodeDTO sendVerifyCodeDTO) {
        return verifyCodeSender.sendCode(sendVerifyCodeDTO);
    }
} 