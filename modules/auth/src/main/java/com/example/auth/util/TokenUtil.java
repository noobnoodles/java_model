package com.example.auth.util;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getSigningKey() {
        // 方法1：使用 Keys.secretKeyFor 生成安全的密钥
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        
        // 或者方法2：从配置的 secret 生成足够长度的密钥
        // return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF8));
    }
    
    /**
     * 生成token
     * @param user 用户信息
     * @return token
     */
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getAccount())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TokenConstants.TOKEN_EXPIRE))
            .signWith(getSigningKey())  // 使用安全的密钥
            .compact();
    }
    
    /**
     * 生成刷新token
     */
    public String generateRefreshToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getAccount())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TokenConstants.REFRESH_TOKEN_EXPIRE))
            .signWith(getSigningKey())  // 使用安全的密钥
            .compact();
    }
    
    /**
     * 验证token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
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
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
} 