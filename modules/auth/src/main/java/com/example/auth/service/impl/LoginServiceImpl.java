package com.example.auth.service.impl;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.dto.LoginDTO;
import com.example.auth.model.entity.User;
import com.example.auth.model.vo.UserVO;
import com.example.auth.mapper.LoginMapper;
import com.example.auth.service.LoginService;
import com.example.auth.util.Token.TokenBuilder;
import com.example.auth.util.Token.TokenParser;
import com.example.auth.util.code.VerifyCodeUtil;
import com.example.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenBuilder tokenBuilder;
    private final TokenParser tokenParser;
    private final VerifyCodeUtil verifyCodeUtil;
    
    @Override
    public UserVO loginByPassword(LoginDTO loginDTO) {
        // 根据登录类型获取用户信息
        User user;

        int loginType = loginDTO.getLoginType();
        if (loginType == 1) {
            user = loginMapper.getUserByUserName(loginDTO.getAccount());
        } else if (loginType == 2) {
            user = loginMapper.getUserByPhone(loginDTO.getAccount());
        } else if (loginType == 3) {
            user = loginMapper.getUserByEmail(loginDTO.getAccount());
        } else {
            throw new BusinessException(400, "不支持的登录类型");
        }

        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }
        
        // 生成token，传入rememberMe参数
        String token = tokenBuilder.generateToken(user, loginDTO.getRememberMe());
        String refreshToken = tokenBuilder.generateRefreshToken(user, loginDTO.getRememberMe());
        
        // 转换为VO并返回
        UserVO userVO = convertToVO(user, token, refreshToken);
        
        // 添加日志输出
        log.info("用户登录成功 - 用户信息：");
        log.info("用户ID: {}", userVO.getId());
        log.info("用户名: {}", userVO.getUsername());
        log.info("记住我: {}", loginDTO.getRememberMe());
        log.info("Token: {}", userVO.getToken());
        log.info("RefreshToken: {}", userVO.getRefreshToken());
        log.info("过期时间: {}", userVO.getExpireTime());
        
        // 验证token
        Claims claims = tokenParser.getClaimsFromToken(token);
        log.info("\nToken解析结果：");
        log.info("用户ID: {}", claims.get("userId"));
        log.info("记住我: {}", claims.get("rememberMe"));
        log.info("签发时间: {}", claims.getIssuedAt());
        log.info("过期时间: {}", claims.getExpiration());
        
        return userVO;
    }
    
    @Override
    public UserVO loginByCode(LoginDTO loginDTO, String code) {
        // 验证验证码
        if (!verifyCodeUtil.verifyCode(loginDTO.getAccount(), code)) {
            throw new BusinessException("验证码无效");
        }
        
        // 根据登录类型获取用户信息
        User user;
        int loginType = loginDTO.getLoginType();
        if (loginType == 1) {
            log.info("user login by Name");
            user = loginMapper.getUserByUserName(loginDTO.getAccount());
        } else if (loginType == 2) {
            log.info("user login by Phone");
            user = loginMapper.getUserByPhone(loginDTO.getAccount());
        } else if (loginType == 3) {
            log.info("user login by Email");
            user = loginMapper.getUserByEmail(loginDTO.getAccount());
        } else {
            throw new BusinessException("不支持的登录类型");
        }
        
        // 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 生成token
        String token = tokenBuilder.generateToken(user, loginDTO.getRememberMe());
        String refreshToken = tokenBuilder.generateRefreshToken(user, loginDTO.getRememberMe());
        
        // 删除验证码
        verifyCodeUtil.deleteCode(loginDTO.getAccount());
        
        // 转换为VO并返回
        return convertToVO(user, token, refreshToken);
    }
    
    @Override
    public void logout(String Token) {

        // TODO: 实现登出逻辑
    }
    
    @Override
    public boolean verifyToken(String token) {
        try {
            // 移除 Bearer 前缀
            if (token.startsWith(TokenConstants.TOKEN_PREFIX)) {
                token = token.substring(TokenConstants.TOKEN_PREFIX.length());
            }
            
            // 验证token
            boolean isValid = tokenParser.validateToken(token);
            
            if (isValid) {
                // 从token中获取用户信息
                Claims claims = tokenParser.getClaimsFromToken(token);
                String userId = claims.get("userId", String.class);
                
                // 检查用户是否存在
                User user = loginMapper.getUserByAccount(userId);
                if (user == null) {
                    log.warn("Token验证成功，但用户不存在 - 用户ID: {}", userId);
                    return false;
                }
                
                log.info("Token验证成功 - 用户ID: {}", userId);
                return true;
            }
            return false;
            
        } catch (Exception e) {
            log.error("Token验证失败", e);
            throw new BusinessException(401, "token验证失败");
        }
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