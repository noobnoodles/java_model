package com.example.auth.util.Token;

import com.example.auth.constant.TokenConstants;
import com.example.auth.model.entity.User;
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
public class TokenBuilder {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成token
     */
    public String generateToken(User user, boolean rememberMe) {
        // 设置JWT头部
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());

        // 设置JWT负载
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getAccount());
        claims.put("sysBelone", user.getSysBelone());
        claims.put("rememberMe", rememberMe);

        long expireTime = rememberMe ? TokenConstants.REMEMBER_ME_TOKEN_EXPIRE : TokenConstants.TOKEN_EXPIRE;

        return Jwts.builder()
            .setHeader(header)                // 设置头部
            .setClaims(claims)                // 设置负载
            .setIssuedAt(new Date())          // 设置签发时间
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 生成刷新token
     */
    public String generateRefreshToken(User user, boolean rememberMe) {
        // 设置JWT头部
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());

        // 设置JWT负载
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getAccount());
        claims.put("sysBelone", user.getSysBelone());
        claims.put("type", "refresh");
        claims.put("rememberMe", rememberMe);

        long expireTime = rememberMe ? TokenConstants.REMEMBER_ME_REFRESH_TOKEN_EXPIRE : TokenConstants.REFRESH_TOKEN_EXPIRE;

        return Jwts.builder()
            .setHeader(header)
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
} 