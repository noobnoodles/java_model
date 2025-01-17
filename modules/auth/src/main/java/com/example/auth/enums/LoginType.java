package com.example.auth.enums;

import lombok.Getter;

@Getter
public enum LoginType {
    
    USERNAME(1, "用户名登录"),
    PHONE(2, "手机号登录"),
    EMAIL(3, "邮箱登录");
    
    private final Integer code;
    private final String desc;
    
    LoginType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static LoginType getByCode(Integer code) {
        for (LoginType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 