package com.example.auth.util.code;

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
    private static final String COOLDOWN_PREFIX = "verify_cooldown:";
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
    public void saveCode(String account, String code) {
        String codeKey = CODE_PREFIX + account;
        String cooldownKey = COOLDOWN_PREFIX + account;
        
        // 保存验证码，设置过期时间
        redisTemplate.opsForValue().set(codeKey, code, CODE_EXPIRE, TimeUnit.MINUTES);
        // 设置冷却时间
        redisTemplate.opsForValue().set(cooldownKey, "1", SEND_COOLDOWN, TimeUnit.MINUTES);
    }
    
    /**
     * 验证验证码
     */
    public boolean verifyCode(String account, String code) {
        String key = CODE_PREFIX + account;
        String savedCode = redisTemplate.opsForValue().get(key);
        return code.equals(savedCode);
    }
    
    /**
     * 检查是否可以发送验证码（是否在冷却时间内）
     */
    public boolean canSendCode(String account) {
        String cooldownKey = COOLDOWN_PREFIX + account;
        return !Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey));
    }
    
    /**
     * 删除验证码
     */
    public void deleteCode(String account) {
        String codeKey = CODE_PREFIX + account;
        String cooldownKey = COOLDOWN_PREFIX + account;
        redisTemplate.delete(codeKey);
        redisTemplate.delete(cooldownKey);
    }
} 