package com.example.auth.service;

import com.example.auth.model.dto.ResetPasswordDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;

public interface PasswordService {
    
    /**
     * 重置密码
     * @param resetPasswordDTO 重置密码参数
     * @return 重置结果(1:成功,0:失败)
     * throws BusinessException 当验证码无效或两次密码不一致时抛出异常
     */
    int resetPassword(ResetPasswordDTO resetPasswordDTO);
    
    /**
     * 修改密码
     * @param sysBelone 系统标识
     * @param account 账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果(1:成功,0:失败)
     * throws BusinessException 当旧密码错误时抛出异常
     */
    int changePassword(String sysBelone, String account, String oldPassword, String newPassword);
    
    /**
     * 验证重置密码的验证码
     * @param sysBelone 系统标识
     * @param account 账号
     * @param code 验证码
     * @return true:有效 false:无效
     */
    boolean verifyResetCode(String sysBelone, String account, String code);

    /**
     * 发送验证码
     * @param sendVerifyCodeDTO 发送验证码参数
     * @return 发送结果(1:成功,0:失败)
     * throws BusinessException 当发送过于频繁或目标无效时抛出异常
     */
    int sendVerifyCode(SendVerifyCodeDTO sendVerifyCodeDTO);
} 