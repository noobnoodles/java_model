package com.example.auth.service;

import com.example.auth.dto.ResetPasswordDTO;

public interface PasswordService {
    
    /**
     * 重置密码
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
    
    /**
     * 修改密码
     */
    void changePassword(String oldPassword, String newPassword);
} 