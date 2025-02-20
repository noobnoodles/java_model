package com.example.auth.service.impl;

import com.example.auth.model.dto.AdminDTO;
import com.example.auth.model.dto.BanDTO;
import com.example.auth.model.entity.User;
import com.example.auth.model.vo.OPVO;
import com.example.auth.model.vo.UserVO;
import com.example.auth.mapper.LoginMapper;
import com.example.auth.mapper.AdminMapper;
import com.example.auth.service.AdminService;
import com.example.auth.util.account.PasswordReset;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;
    private final PasswordReset passwordReset;

    @Override
    public UserVO adminLogin(AdminDTO adminDTO) {
        // 获取管理员账号信息（account为2710的用户）
        User admin = loginMapper.getUserByAccount("2710");
        if (admin == null) {
            throw new BusinessException(400, "管理员账号不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(adminDTO.getPassword(), admin.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }

        // 验证用户信息是否与管理员完全匹配
        if (!admin.getAccount().equals(adminDTO.getAccount()) ||
            !admin.getUsername().equals(adminDTO.getUsername()) ||
            !admin.getPhone().equals(adminDTO.getPhone()) ||
            !admin.getEmail().equals(adminDTO.getEmail())) {
            throw new BusinessException(400, "非管理员用户");
        }

        log.info("管理员登录成功 - 用户ID: {}", admin.getAccount());
        
        return convertToVO(admin);
    }

    @Override
    public List<OPVO> getUsers() {
        // 从数据库获取所有用户
        List<User> users = loginMapper.getAllUsers();
        
        // 转换为OPVO列表
        return users.stream()
            .map(user -> {
                OPVO opvo = new OPVO();
                opvo.setId(user.getAccount());
                opvo.setUsername(user.getUsername());
                opvo.setPhone(user.getPhone());
                opvo.setEmail(user.getEmail());
                return opvo;
            })
            .collect(Collectors.toList());
    }

    @Override
    public void toggleUserStatus(BanDTO banDTO) {
        // 验证用户是否存在
        User user = loginMapper.getUserByAccount(banDTO.getUserid());
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }

        if (banDTO.getStatus()) {
            // 解除封禁
            int rows = adminMapper.removeForbid(banDTO.getUserid());
            if (rows == 0) {
                throw new BusinessException(400, "用户未被封禁");
            }
            log.info("用户{}已解除封禁", banDTO.getUserid());
        } else {
            // 封禁用户
            LocalDateTime startTime = banDTO.getStartTime();
            if (startTime == null) {
                startTime = LocalDateTime.now();
            }
            
            int rows = adminMapper.createForbid(
                banDTO.getUserid(),
                banDTO.getDuration(),
                startTime
            );
            
            if (rows == 0) {
                throw new BusinessException(400, "封禁失败");
            }
            log.info("用户{}已被封禁，持续时间：{}小时，开始时间：{}", 
                banDTO.getUserid(), banDTO.getDuration(), startTime);
        }
    }

    @Override
    public void resetUserPassword(String userid) {
        // 验证用户是否存在
        User user = loginMapper.getUserByAccount(userid);
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }

        int rows = passwordReset.resetToDefault(userid);
        if (rows == 0) {
            throw new BusinessException(400, "密码重置失败");
        }
        log.info("管理员重置用户{}密码成功", userid);
    }

    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getAccount());
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        return vo;
    }
} 