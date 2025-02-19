package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求参数")
public class LoginDTO {

    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "登录类型(1:用户名 2:手机号 3:邮箱)")
    private Integer loginType;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "记住我")
    private Boolean rememberMe = false;
     
} 