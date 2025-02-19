package com.example.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PasswordMapper {
    
    /**
     * 更新用户密码（通过账号）
     * @param account 账号
     * @param password 新密码
     * @return 更新结果(1:成功,0:失败)
     */
    @Update("UPDATE user_info SET password = #{password} WHERE account = #{account}")
    int updatePassword(
        @Param("account") String account, 
        @Param("password") String password
    );
    
    /**
     * 更新用户密码（通过邮箱）
     * @param email 邮箱
     * @param password 新密码
     * @return 更新结果(1:成功,0:失败)
     */
    @Update("UPDATE user_info SET password = #{password} WHERE email = #{email}")
    int updatePasswordByEmail(
        @Param("email") String email, 
        @Param("password") String password
    );
} 