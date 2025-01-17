package com.example.auth.service.impl;

import com.example.auth.dto.LoginDTO;
import com.example.auth.service.LoginService;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider tokenProvider;
    
    @Override
    public String login(LoginDTO loginDTO) {
        // 根据登录类型获取用户信息
        User user = null;
        LoginType type = LoginType.getByCode(loginDTO.getLoginType());
        
        if (type == null) {
            throw new BusinessException("不支持的登录类型");
        }
        
        switch (type) {
            case USERNAME:
                user = userMapper.findByUsername(loginDTO.getAccount());
                break;
            case PHONE:
                user = userMapper.findByPhone(loginDTO.getAccount());
                break;
            case EMAIL:
                user = userMapper.findByEmail(loginDTO.getAccount());
                break;
        }
        
        // 验证用户存在性
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        // 生成token
        return tokenProvider.generateToken(user);
    }
    
    @Override
    public void logout() {
        // TODO: 实现登出逻辑
    }
    
    @Override
    public String refreshToken() {
        // TODO: 实现令牌刷新逻辑
        return null;
    }
} 