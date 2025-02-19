package com.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "用户基本数据修改参数")
public class InforChangeDTO {

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别")
    private short sexual;

    @Schema(description = "用户名")
    @NotNull
    private String userName;

    @Schema(description = "个性签名")
    private String sign;

    @Schema(description = "账号标识")
    @NotNull
    private String accountID;

    @Schema(description = "系统标识")
    @NotNull
    private String sysBelone;

}
