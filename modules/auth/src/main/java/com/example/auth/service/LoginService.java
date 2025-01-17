package com.example.auth.service;

import com.example.auth.dto.LoginDTO;

public interface LoginService {
    
    /**
     * 用户登录
     */
    String login(LoginDTO loginDTO);
    
    /**
     * 用户登出
     */
    void logout();
    
    /**
     * 刷新令牌
     */
    String refreshToken();
} 