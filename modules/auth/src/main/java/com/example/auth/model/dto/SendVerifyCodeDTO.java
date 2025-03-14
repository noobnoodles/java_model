package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "发送验证码请求参数")
public class SendVerifyCodeDTO {
    
    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;
    
    @Schema(description = "发送目标(手机号/邮箱)")
    @NotBlank(message = "发送目标不能为空")
    private String target;
    
    @Schema(description = "目标类型(1:手机号 2:邮箱)")
    private Integer targetType = 2;  // 默认为邮箱类型
    

    @Schema(description = "验证码")
    private String code;
} 