package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "重置密码请求参数")
public class ResetPasswordDTO {
    
    @Schema(description = "账号(用户名/手机号/邮箱)")
    @NotBlank(message = "账号不能为空")
    private String account;
    
    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    
    @Schema(description = "确认新密码")
    @NotBlank(message = "确认新密码不能为空")
    private String confirmPassword;
    
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
} 