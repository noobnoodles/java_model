package com.example.auth.service;

import com.example.auth.model.dto.ForgetPasswordDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;

public interface PasswordService {
    int resetPassword(ForgetPasswordDTO forgetPasswordDTO);
    int changePassword(ForgetPasswordDTO forgetPasswordDTO);
    boolean verifyResetCode(String account, String code);
    int sendVerifyCode(SendVerifyCodeDTO sendVerifyCodeDTO);
} 