package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "注册请求参数")
public class RegisterDTO {
    
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @Schema(description = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
} 