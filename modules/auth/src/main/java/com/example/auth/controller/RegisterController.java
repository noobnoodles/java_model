package com.example.auth.controller;

import com.example.auth.model.dto.RegisterDTO;
import com.example.auth.model.entity.User;
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
        User user = new User();
        user.setAccount(registerDTO.getAccount());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setSysBelone(registerDTO.getSysBelone());
        
        // 执行注册
        int result = registerService.register(user);
        return result > 0 ? R.ok() : R.fail("注册失败");
    }
    
    @Operation(summary = "检查用户名是否可用")
    @GetMapping("/check-username")
    public R<Boolean> checkUsername(@RequestParam String username, @RequestParam String sysBelone) {
        return R.ok(registerService.checkUsernameAvailable(username, sysBelone));
    }
    
    @Operation(summary = "检查账号是否可用")
    @GetMapping("/check-account")
    public R<Boolean> checkAccount(@RequestParam String account, @RequestParam String sysBelone) {
        return R.ok(registerService.checkAccountAvailable(account, sysBelone));
    }
    
    @Operation(summary = "检查邮箱是否可用")
    @GetMapping("/check-email")
    public R<Boolean> checkEmail(@RequestParam String email, @RequestParam String sysBelone) {
        return R.ok(registerService.checkEmailAvailable(email, sysBelone));
    }
} 