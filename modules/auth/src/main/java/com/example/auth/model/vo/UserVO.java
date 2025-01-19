package com.example.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "用户信息")
public class UserVO {

    @Schema(description = "用户ID")
    private String id;

    @Schema(description = "用户名")
    private String username;


    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "token过期时间")
    private Long expireTime;
} 