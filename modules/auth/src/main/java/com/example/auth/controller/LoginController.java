package com.example.auth.controller;

import com.example.auth.model.dto.LoginDTO;
import com.example.auth.model.vo.UserVO;
import com.example.auth.service.LoginService;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "登录管理")
@CrossOrigin(originPatterns = "http://localhost:[*]", allowCredentials = "true")
@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "密码登录")
    @PostMapping("/password")
    public R<UserVO> loginByPassword(@Validated @RequestBody LoginDTO loginDTO) {
        UserVO userVO = loginService.loginByPassword(loginDTO);
        return R.ok(userVO);
    }

    @Operation(summary = "验证码登录")
    @PostMapping("/code")
    public R<UserVO> loginByCode(@Validated @RequestBody LoginDTO loginDTO, @RequestParam String code) {
        UserVO userVO = loginService.loginByCode(loginDTO, code);
        return R.ok(userVO);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public R<Void> logout(@RequestHeader("Authorization") String token) {
        loginService.logout(token);
        return R.ok();
    }
} 