package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "忘记密码请求参数")
public class ForgetPasswordDTO {
    
    @Schema(description = "发送目标(邮箱)")
    @NotBlank(message = "发送目标不能为空")
    private String target;
    
    @Schema(description = "新密码")
    private String newPassword;
    
    @Schema(description = "确认新密码")
    private String confirmPassword;
    
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
    
} 