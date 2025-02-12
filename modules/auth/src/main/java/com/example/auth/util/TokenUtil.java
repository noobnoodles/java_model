package com.example.auth.util;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * 生成token
     */
    public String generateToken(User user) {
        // 设置JWT头部
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());
        
        // 设置JWT负载（只保留认证必需信息）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getAccount());     // 用户账号ID
        claims.put("sysBelone", user.getSysBelone());// 系统归属
        
        return Jwts.builder()
            .setHeader(header)                // 设置头部
            .setClaims(claims)                // 设置负载
            .setIssuedAt(new Date())          // 设置签发时间
            .setExpiration(new Date(System.currentTimeMillis() + TokenConstants.TOKEN_EXPIRE))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    /**
     * 生成刷新token
     */
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getAccount());
        claims.put("sysBelone", user.getSysBelone());
        claims.put("type", "refresh");
        
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TokenConstants.REFRESH_TOKEN_EXPIRE))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
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
     * 从token中获取用户信息
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    /**
     * 从token中获取用户ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", String.class);
    }
    
    /**
     * 从token中获取系统归属
     */
    public String getSysBeloneFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("sysBelone", String.class);
    }
} 