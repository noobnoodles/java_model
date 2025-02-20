package com.example.auth.controller;

import com.example.auth.model.dto.AdminDTO;
import com.example.auth.model.dto.BanDTO;
import com.example.auth.model.vo.UserVO;
import com.example.auth.model.vo.OPVO;
import com.example.auth.service.AdminService;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "管理员接口")
@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public R<UserVO> adminLogin(@Validated @RequestBody AdminDTO adminDTO) {
        UserVO userVO = adminService.adminLogin(adminDTO);
        return R.ok(userVO);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/users")
    public R<List<OPVO>> getUsers() {
        List<OPVO> users = adminService.getUsers();
        return R.ok(users);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/users/{userid}/reset-password")
    public R<Void> resetUser(@PathVariable String userid) {
        adminService.resetUserPassword(userid);
        return R.ok();
    }

    @Operation(summary = "封禁/解封用户")
    @PutMapping("/users/{userid}/status")
    public R<Void> toggleUserStatus(@Validated @RequestBody BanDTO banDTO) {
        adminService.toggleUserStatus(banDTO);
        return R.ok();
    }
}
