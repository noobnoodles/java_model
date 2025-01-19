package com.example.auth.controller;

import com.example.auth.model.dto.RegisterDTO;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "注册管理")
@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    @Operation(summary = "用户注册")
    @PostMapping
    public R<Void> register(@Validated @RequestBody RegisterDTO registerDTO) {
        // TODO: 实现注册逻辑
        return R.ok();
    }

} 