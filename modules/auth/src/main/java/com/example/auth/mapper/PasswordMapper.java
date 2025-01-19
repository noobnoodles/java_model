package com.example.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PasswordMapper {
    
    /**
     * 更新用户密码
     * @param sysBelone 系统标识
     * @param account 账号
     * @param password 新密码
     * @return 更新结果(1:成功,0:失败)
     */
    @Update("UPDATE user_info SET password = #{password} WHERE account = #{account} AND sys_belone = #{sysBelone}")
    int updatePassword(String sysBelone, String account, String password);
} 