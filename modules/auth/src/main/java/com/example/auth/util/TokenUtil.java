package com.example.auth.util;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    
    // 根据密钥字符串生成加密密钥
    private SecretKey getSignKey() {
        byte[] keyBytes = TokenConstants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * 生成token
     * @param user 用户信息
     * @return token
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + TokenConstants.TOKEN_EXPIRE);
        
        return Jwts.builder()
                .setSubject(user.getAccount())
                .claim("username", user.getUsername())
                .claim("sysBelone", user.getSysBelone())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(getSignKey())
                .compact();
    }
    
    /**
     * 生成刷新token
     */
    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + TokenConstants.REFRESH_TOKEN_EXPIRE);
        
        return Jwts.builder()
                .setSubject(user.getAccount())
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(getSignKey())
                .compact();
    }
    
    /**
     * 验证token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 从token中获取用户账号
     */
    public String getAccountFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
} 