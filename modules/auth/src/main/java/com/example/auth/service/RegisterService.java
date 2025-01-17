package com.example.auth.service;

import com.example.auth.dto.RegisterDTO;
import com.example.auth.vo.UserVO;

public interface RegisterService {
    
    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功的用户信息
     */
    UserVO register(RegisterDTO registerDTO);
} 