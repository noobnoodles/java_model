package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "发送验证码请求参数")
public class SendCodeDTO {
    
    @Schema(description = "账号(用户名/手机号/邮箱)")
    @NotBlank(message = "账号不能为空")
    private String account;
    
    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;
} 