package com.example.auth.constant;

/**
 * Token常量配置
 */
public interface TokenConstants {
    
    /** token前缀 */
    String TOKEN_PREFIX = "Bearer ";
    
    /** token头部 */
    String TOKEN_HEADER = "Authorization";
    
    /** token密钥 */
    String TOKEN_SECRET = "your-secret-key";
    
    /** token有效期（24小时） */
    long TOKEN_EXPIRE = 24 * 60 * 60 * 1000L;
    
    /** 刷新token有效期（7天） */
    long REFRESH_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000L;
    
    /** token类型 */
    interface TokenType {
        /** 访问token */
        String ACCESS = "access";
        /** 刷新token */
        String REFRESH = "refresh";
    }
    
    /** token状态 */
    interface TokenStatus {
        /** 有效的 */
        int VALID = 1;
        /** 过期的 */
        int EXPIRED = 2;
        /** 无效的 */
        int INVALID = 3;
    }
    
    /** token存储前缀 */
    interface RedisKey {
        /** 访问token前缀 */
        String TOKEN_PREFIX = "auth:token:";
        /** 刷新token前缀 */
        String REFRESH_TOKEN_PREFIX = "auth:refresh_token:";
        /** 黑名单token前缀 */
        String BLACK_TOKEN_PREFIX = "auth:black_token:";
    }
} 