package com.example.auth.util;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class VerifyCodeUtil {
    
    private final StringRedisTemplate redisTemplate;
    
    // Redis key前缀
    private static final String CODE_PREFIX = "verify_code:";
    // 验证码有效期（分钟）
    private static final long CODE_EXPIRE = 5;
    // 发送冷却时间（分钟）
    private static final long SEND_COOLDOWN = 1;
    
    /**
     * 生成验证码
     */
    public String generateCode() {
        return RandomUtil.randomNumbers(6);
    }
    
    /**
     * 保存验证码
     */
    public void saveCode(String sysBelone, String account, String code) {
        String key = CODE_PREFIX + sysBelone + ":" + account;
        redisTemplate.opsForValue().set(key, code, CODE_EXPIRE, TimeUnit.MINUTES);
    }
    
    /**
     * 验证验证码
     */
    public boolean verifyCode(String sysBelone, String account, String code) {
        String key = CODE_PREFIX + sysBelone + ":" + account;
        String savedCode = redisTemplate.opsForValue().get(key);
        return code.equals(savedCode);
    }
    
    /**
     * 检查是否可以发送验证码
     */
    public boolean canSendCode(String sysBelone, String account) {
        String key = CODE_PREFIX + sysBelone + ":" + account;
        return !Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * 删除验证码
     */
    public void deleteCode(String sysBelone, String account) {
        String key = CODE_PREFIX + sysBelone + ":" + account;
        redisTemplate.delete(key);
    }
} 