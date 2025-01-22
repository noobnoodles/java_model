package com.example.auth.mapper;

import com.example.auth.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    
    @Select("SELECT * FROM user_info WHERE account = #{account} AND sys_belone = #{sysBelone}")
    User getUserByAccount(String account, String sysBelone);
    
    @Select("SELECT * FROM user_info WHERE phone = #{phone} AND sys_belone = #{sysBelone}")
    User getUserByPhone(String phone, String sysBelone);
    
    @Select("SELECT * FROM user_info WHERE email = #{email} AND sys_belone = #{sysBelone}")
    User getUserByEmail(String email, String sysBelone);

    @Select("SELECT * FROM user_info WHERE username = #{account} AND sys_belone = #{sysBelone}")
    User getUserByUserName(String account, String sysBelone);
}