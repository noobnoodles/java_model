package com.example.auth.controller;

import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {

    @Operation(summary = "用户登录")
    @PostMapping
    public R<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        // TODO: 实现登录逻辑
        return R.ok("登录成功");
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public R<Void> logout() {
        // TODO: 实现登出逻辑
        return R.ok();
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh")
    public R<String> refresh() {
        // TODO: 实现令牌刷新逻辑
        return R.ok();
    }
} 