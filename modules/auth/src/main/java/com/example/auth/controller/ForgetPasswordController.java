package com.example.auth.controller;

import com.example.auth.model.dto.ResetPasswordDTO;
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
@RestController
@RequestMapping("/auth/forget-password")
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public R<Void> resetPassword(@Validated @RequestBody ResetPasswordDTO resetPasswordDTO) {
        int result = passwordService.resetPassword(resetPasswordDTO);
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
    public R<Void> changePassword(@RequestParam String sysBelone,
                                @RequestParam String account,
                                @RequestParam String oldPassword,
                                @RequestParam String newPassword) {
        int result = passwordService.changePassword(sysBelone, account, oldPassword, newPassword);
        return result > 0 ? R.ok() : R.fail("修改密码失败");
    }

    @Operation(summary = "验证重置码")
    @GetMapping("/verify")
    public R<Boolean> verifyResetCode(@RequestParam String sysBelone,
                                    @RequestParam String account,
                                    @RequestParam String code) {
        boolean result = passwordService.verifyResetCode(sysBelone, account, code);
        return R.ok(result);
    }
} 