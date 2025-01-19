package com.example.auth.service.impl;

import com.example.auth.mapper.PasswordMapper;
import com.example.auth.model.dto.ResetPasswordDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import com.example.auth.service.PasswordService;
import com.example.auth.util.VerifyCodeSender;
import com.example.auth.util.VerifyCodeUtil;
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
    public int resetPassword(ResetPasswordDTO resetPasswordDTO) {
        // 验证验证码
        if (!verifyResetCode(resetPasswordDTO.getSysBelone(), resetPasswordDTO.getAccount(), resetPasswordDTO.getCode())) {
            throw new BusinessException("验证码无效");
        }
        
        // 更新为默认密码
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        int rows = passwordMapper.updatePassword(resetPasswordDTO.getSysBelone(), 
                                               resetPasswordDTO.getAccount(), 
                                               encodedPassword);
        
        if (rows > 0) {
            log.info("重置密码成功: {}", resetPasswordDTO.getAccount());
            // 删除验证码
            verifyCodeUtil.deleteCode(resetPasswordDTO.getSysBelone(), resetPasswordDTO.getAccount());
        } else {
            log.error("[密码重置] 用户:{} 系统:{} 重置密码失败", 
                    resetPasswordDTO.getAccount(), resetPasswordDTO.getSysBelone());
        }
        
        return rows;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changePassword(String sysBelone, String account, String oldPassword, String newPassword) {
        // 更新密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        int rows = passwordMapper.updatePassword(sysBelone, account, encodedPassword);
        
        if (rows > 0) {
            log.info("[密码修改] 用户:{} 系统:{} 修改密码成功", account, sysBelone);
        } else {
            log.error("[密码修改] 用户:{} 系统:{} 修改密码失败", account, sysBelone);
        }
        
        return rows;
    }
    
    @Override
    public boolean verifyResetCode(String sysBelone, String account, String code) {
        return verifyCodeUtil.verifyCode(sysBelone, account, code);
    }
    
    @Override
    public int sendVerifyCode(SendVerifyCodeDTO sendVerifyCodeDTO) {
        return verifyCodeSender.sendCode(sendVerifyCodeDTO);
    }
} 