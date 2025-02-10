package com.example.auth.service.impl;

import com.example.auth.model.entity.User;
import com.example.auth.mapper.RegisterMapper;
import com.example.auth.service.RegisterService;
import com.example.auth.util.AccountNumCreate;
import com.example.auth.model.dto.RegisterDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;
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
public class RegisterServiceImpl implements RegisterService {

    private final RegisterMapper registerMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountNumCreate accountNumCreate;
    private final VerifyCodeSender verifyCodeSender;
    private final VerifyCodeUtil verifyCodeUtil;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(RegisterDTO registerDTO) {
        // 验证两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 验证邮箱验证码
        boolean isCodeValid = verifyCodeUtil.verifyCode(
            registerDTO.getSysBelone(),
            registerDTO.getEmail(),  // 使用邮箱作为账号
            registerDTO.getEmailCode()
        );
        
        if (!isCodeValid) {
            throw new BusinessException("邮箱验证码错误或已过期");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setSysBelone(registerDTO.getSysBelone());
        
        // 生成账号
        String account = accountNumCreate.createNewAccount(registerDTO.getSysBelone());
        user.setAccount(account);
        
        // 检查用户名是否已存在
        if (!checkUsernameAvailable(user.getUsername(), user.getSysBelone())) {
            throw new BusinessException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (user.getEmail() != null && registerMapper.checkEmailExists(user.getEmail(), user.getSysBelone()) > 0) {
            throw new BusinessException("邮箱已被使用");
        }
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 注册用户
        int rows = registerMapper.insertUser(user);
        
        if (rows > 0) {
            // 注册成功后删除验证码
            verifyCodeUtil.deleteCode(registerDTO.getSysBelone(), registerDTO.getEmail());
            log.info("用户注册成功: {}", user.getAccount());
        } else {
            log.error("用户注册失败: {}", user.getAccount());
        }
        
        return rows;
    }
    
    @Override
    public boolean checkUsernameAvailable(String username, String sysBelone) {
        return registerMapper.checkUsernameExists(username, sysBelone) == 0;
    }
    
    @Override
    public boolean checkAccountAvailable(String account, String sysBelone) {
        return registerMapper.checkAccountExists(account, sysBelone) == 0;
    }
    
    @Override
    public boolean checkEmailAvailable(String email, String sysBelone) {
        // 检查邮箱是否已存在
        return registerMapper.checkEmailExists(email, sysBelone) == 0;
    }

    @Override
    public boolean sendEmailCode(String email, String sysBelone) {
        try {
            // 构建验证码发送请求
            SendVerifyCodeDTO sendVerifyCodeDTO = new SendVerifyCodeDTO();
            sendVerifyCodeDTO.setAccount(email);  // 使用邮箱作为账号
            sendVerifyCodeDTO.setTarget(email);   // 发送目标为邮箱
            sendVerifyCodeDTO.setTargetType(2);   // 2表示邮箱
            sendVerifyCodeDTO.setSysBelone(sysBelone);
            
            // 发送验证码邮件
            int sendResult = verifyCodeSender.sendCode(sendVerifyCodeDTO);
            if (sendResult != 1) {
                throw new BusinessException("验证码发送失败");
            }
            
            return true;
            
        } catch (Exception e) {
            log.error("[邮箱验证码] 发送失败：{}", e.getMessage());
            throw new BusinessException("验证码发送失败：" + e.getMessage());
        }
    }
} 