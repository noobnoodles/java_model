package com.example.auth.service.impl;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.dto.LoginDTO;
import com.example.auth.model.entity.User;
import com.example.auth.model.vo.UserVO;
import com.example.auth.mapper.LoginMapper;
import com.example.auth.service.LoginService;
import com.example.auth.util.TokenUtil;
import com.example.auth.util.VerifyCodeUtil;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final VerifyCodeUtil verifyCodeUtil;
    
    @Override
    public UserVO loginByPassword(LoginDTO loginDTO) {
        // 根据登录类型获取用户信息
        User user = switch (loginDTO.getLoginType()) {
            case 1 -> loginMapper.getUserByAccount(loginDTO.getAccount(), loginDTO.getSysBelone());
            case 2 -> loginMapper.getUserByPhone(loginDTO.getAccount(), loginDTO.getSysBelone());
            case 3 -> loginMapper.getUserByEmail(loginDTO.getAccount(), loginDTO.getSysBelone());
            default -> throw new BusinessException("不支持的登录类型");
        };
        
        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        // 生成token
        String token = tokenUtil.generateToken(user);
        String refreshToken = tokenUtil.generateRefreshToken(user);
        
        // 转换为VO并返回
        return convertToVO(user, token, refreshToken);
    }
    
    @Override
    public UserVO loginByCode(LoginDTO loginDTO, String code) {
        // 验证验证码
        if (!verifyCodeUtil.verifyCode(loginDTO.getSysBelone(), loginDTO.getAccount(), code)) {
            throw new BusinessException("验证码无效");
        }
        
        // 根据登录类型获取用户信息
        User user = switch (loginDTO.getLoginType()) {
            case 1 -> loginMapper.getUserByAccount(loginDTO.getAccount(), loginDTO.getSysBelone());
            case 2 -> loginMapper.getUserByPhone(loginDTO.getAccount(), loginDTO.getSysBelone());
            case 3 -> loginMapper.getUserByEmail(loginDTO.getAccount(), loginDTO.getSysBelone());
            default -> throw new BusinessException("不支持的登录类型");
        };
        
        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 生成token
        String token = tokenUtil.generateToken(user);
        String refreshToken = tokenUtil.generateRefreshToken(user);
        
        // 删除验证码
        verifyCodeUtil.deleteCode(loginDTO.getSysBelone(), loginDTO.getAccount());
        
        // 转换为VO并返回
        return convertToVO(user, token, refreshToken);
    }
    
    @Override
    public void logout(String Token) {

        // TODO: 实现登出逻辑
    }
    

    private UserVO convertToVO(User user, String token, String refreshToken) {
        UserVO vo = new UserVO();
        vo.setId(user.getAccount());
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        vo.setToken(token);
        vo.setRefreshToken(refreshToken);
        vo.setExpireTime(System.currentTimeMillis() + TokenConstants.TOKEN_EXPIRE);
        return vo;
    }
} 