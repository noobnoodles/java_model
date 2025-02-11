package com.example.auth.controller;

import com.example.auth.model.dto.ForgetPasswordDTO;
import com.example.auth.model.dto.SendVerifyCodeDTO;
import com.example.auth.service.PasswordService;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "忘记密码")
@CrossOrigin(originPatterns = "http://localhost:[*]", allowCredentials = "true")
@RestController
@RequestMapping("/auth/forget-password")
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public R<Void> resetPassword(@Validated @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        int result = passwordService.resetPassword(forgetPasswordDTO);
        return result > 0 ? R.ok() : R.fail("重置密码失败");
    }

    @Operation(summary = "发送重置验证码")
    @PostMapping("/code")
    public R<Void> sendResetCode(@Validated @RequestBody SendVerifyCodeDTO sendVerifyCodeDTO) {
        int result = passwordService.sendVerifyCode(sendVerifyCodeDTO);
        return result > 0 ? R.ok() : R.fail("验证码发送失败");
    }

    @Operation(summary = "修改密码")
    @PostMapping("/change")
    public R<Void> changePassword(@Validated @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        int result = passwordService.changePassword(forgetPasswordDTO);
        return result > 0 ? R.ok() : R.fail("修改密码失败");
    }
} 