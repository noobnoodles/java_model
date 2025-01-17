package com.example.auth.service.impl;

import com.example.auth.dto.RegisterDTO;
import com.example.auth.service.RegisterService;
import com.example.auth.vo.UserVO;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(RegisterDTO registerDTO) {
        log.info("开始注册用户：{}", registerDTO.getUsername());
        
        // 验证用户名是否已存在
        if (userMapper.findByUsername(registerDTO.getUsername()) != null) {
            log.warn("用户名已存在：{}", registerDTO.getUsername());
            throw new BusinessException(400, "用户名已存在");
        }
        
        // 验证两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            log.warn("两次输入的密码不一致");
            throw new BusinessException(400, "两次输入的密码不一致");
        }
        
        try {
            // 创建用户
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setStatus(1); // 1: 正常状态
            
            // 保存用户
            userMapper.insert(user);
            log.info("用户注册成功：{}", user.getUsername());
            
            // 转换为VO返回
            return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
                
        } catch (Exception e) {
            log.error("用户注册失败：", e);
            throw new BusinessException(500, "注册失败，请稍后重试");
        }
    }
} 