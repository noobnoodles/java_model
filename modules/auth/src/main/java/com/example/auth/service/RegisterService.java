package com.example.auth.service;

import com.example.auth.model.dto.RegisterDTO;

public interface RegisterService {
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果(1:成功,0:失败)
     * throws BusinessException 当用户名/账号/邮箱已存在时抛出异常
     */
    int register(RegisterDTO registerDTO);
    
    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @return true:可用 false:已存在
     */
    boolean checkUsernameAvailable(String username);
    
    /**
     * 检查账号是否可用
     * @param account 账号
     * @return true:可用 false:已存在
     */
    boolean checkAccountAvailable(String account);
    
    /**
     * 检查邮箱是否可用
     * @param email 邮箱
     * @return true:可用 false:已存在
     */
    boolean checkEmailAvailable(String email);

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return true:发送成功 false:发送失败
     */
    boolean sendEmailCode(String email);
} 