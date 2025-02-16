package com.example.auth.service;

import com.example.auth.model.dto.LoginDTO;
import com.example.auth.model.vo.UserVO;

public interface LoginService {
    
    /**
     * 密码登录
     * @param loginDTO 登录参数
     * @return 用户信息和token
     * throws BusinessException 当用户不存在或密码错误时抛出异常
     */
    UserVO loginByPassword(LoginDTO loginDTO);
    
    /**
     * 验证码登录
     * @param loginDTO 登录参数
     * @param code 验证码
     * @return 用户信息和token
     * throws BusinessException 当用户不存在或验证码无效时抛出异常
     */
    UserVO loginByCode(LoginDTO loginDTO, String code);
    
    /**
     * 用户登出
     * @param token 访问令牌
     * throws BusinessException 当token无效时抛出异常
     */
    void logout(String token);
    
    /**
     * 验证token
     * @param token 访问令牌
     * @return 验证结果
     */
    boolean verifyToken(String token);
} 
