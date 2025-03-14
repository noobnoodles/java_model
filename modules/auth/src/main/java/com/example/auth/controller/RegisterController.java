package com.example.auth.controller;

import com.example.auth.model.dto.RegisterDTO;
import com.example.auth.service.RegisterService;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "注册管理")
@CrossOrigin(originPatterns = "http://localhost:[*]", allowCredentials = "true")
@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    
    private final RegisterService registerService;

    @Operation(summary = "用户注册")
    @PostMapping
    public R<Void> register(@Validated @RequestBody RegisterDTO registerDTO) {
        // 转换DTO为实体
        // 执行注册
        int result = registerService.register(registerDTO);
        return result > 0 ? R.ok() : R.fail("注册失败");
    }
    
    @Operation(summary = "检查用户名是否可用")
    @GetMapping("/check-username")
    public R<Boolean> checkUsername(@RequestParam String username) {
        return R.ok(registerService.checkUsernameAvailable(username));
    }
    
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/send-email-code")
    public R<Void> sendEmailCode(
        @RequestParam("email") String email
    ) {
        // 先检查邮箱是否可用
        if (!registerService.checkEmailAvailable(email)) {
            return R.fail("该邮箱已被使用");
        }
        
        // 发送验证码
        if (registerService.sendEmailCode(email)) {
            return R.ok();
        } else {
            return R.fail("验证码发送失败");
        }
    }
} 