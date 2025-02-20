package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminDTO {
    @Schema(description = "管理员账号")
    private String account;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "密码")
    private String password;
}
