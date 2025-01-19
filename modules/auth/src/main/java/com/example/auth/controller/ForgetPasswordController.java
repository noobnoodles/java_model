package com.example.auth.controller;

import com.example.auth.model.dto.ResetPasswordDTO;
import com.example.auth.model.dto.SendCodeDTO;
import com.example.common.core.model.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "忘记密码")
@RestController
@RequestMapping("/auth/forget-password")
@RequiredArgsConstructor
public class ForgetPasswordController {

    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public R<Void> resetPassword(@Validated @RequestBody ResetPasswordDTO resetPasswordDTO) {
        // TODO: 实现重置密码逻辑
        return R.ok();
    }

    @Operation(summary = "发送重置验证码")
    @PostMapping("/code")
    public R<Void> sendResetCode(@Validated @RequestBody SendCodeDTO sendCodeDTO) {
        // TODO: 实现发送验证码逻辑
        return R.ok();
    }
} 