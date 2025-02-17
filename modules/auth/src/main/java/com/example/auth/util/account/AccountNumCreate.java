package com.example.auth.util.account;

import com.example.auth.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountNumCreate {
    
    private final RegisterMapper registerMapper;
    
    // 默认偏移值，可以根据需要调整
    private static final int OFFSET = 10000;

    public String createNewAccount(String sysBelone) {
        // 从数据库查询当前系统的用户总数
        int count = registerMapper.countUserNumber(sysBelone);

        // 计算新账号（总数 + 偏移值）
        int newAccountNum = count + OFFSET;
        
        // 转换为16进制并补齐位数（至少4位）
        String hexAccount = Integer.toHexString(newAccountNum).toUpperCase();
        return String.format("%4s", hexAccount).replace(' ', '0');
    }
    
    /**
     * 使用自定义偏移值生成新账号
     * @param sysBelone 所属系统
     * @param customOffset 自定义偏移值
     * @return 16进制格式的账号
     */
    public String createNewAccount(String sysBelone, int customOffset) {
        // 从数据库查询当前系统的用户总数
        int count = registerMapper.countUserNumber(sysBelone);
        
        int newAccountNum = count + customOffset;
        String hexAccount = Integer.toHexString(newAccountNum).toUpperCase();
        return String.format("%4s", hexAccount).replace(' ', '0');
    }
}
