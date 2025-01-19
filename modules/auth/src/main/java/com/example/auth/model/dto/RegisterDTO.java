package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "注册请求参数")
public class RegisterDTO {
    
    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;
    
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @Schema(description = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Schema(description = "所属系统")
    @NotBlank(message = "所属系统不能为空")
    private String sysBelone;
} 