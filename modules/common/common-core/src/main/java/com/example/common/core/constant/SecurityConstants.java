package com.example.common.core.constant;

public interface SecurityConstants {
    
    String TOKEN_PREFIX = "Bearer ";
    
    String TOKEN_HEADER = "Authorization";
    
    String TOKEN_SECRET = "your-secret-key";
    
    long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000L; // 24小时
} 