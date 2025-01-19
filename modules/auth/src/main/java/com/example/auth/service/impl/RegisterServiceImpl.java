package com.example.auth.service.impl;

import com.example.auth.model.entity.User;
import com.example.auth.mapper.RegisterMapper;
import com.example.auth.service.RegisterService;
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
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(User user) {
        // 检查账号是否已存在
        if (!checkAccountAvailable(user.getAccount(), user.getSysBelone())) {
            throw new BusinessException("账号已存在");
        }
        
        // 检查用户名是否已存在
        if (!checkUsernameAvailable(user.getUsername(), user.getSysBelone())) {
            throw new BusinessException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (user.getEmail() != null && !checkEmailAvailable(user.getEmail(), user.getSysBelone())) {
            throw new BusinessException("邮箱已被使用");
        }
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 注册用户
        int rows = registerMapper.insertUser(user);
        
        if (rows > 0) {
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
        return registerMapper.checkEmailExists(email, sysBelone) == 0;
    }
} 