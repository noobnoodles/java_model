package com.example.auth.mapper;

import com.example.auth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    
    @Select("SELECT * FROM user_info WHERE account = #{account} AND sys_belone = #{sysBelone}")
    User getUserByAccount(
        @Param("account") String account, 
        @Param("sysBelone") String sysBelone
    );
    
    @Select("SELECT * FROM user_info WHERE phone = #{phone} AND sys_belone = #{sysBelone}")
    User getUserByPhone(
        @Param("phone") String phone, 
        @Param("sysBelone") String sysBelone
    );
    
    @Select("SELECT * FROM user_info WHERE email = #{email} AND sys_belone = #{sysBelone}")
    User getUserByEmail(
        @Param("email") String email, 
        @Param("sysBelone") String sysBelone
    );

    @Select("SELECT * FROM user_info WHERE username = #{userName} AND sys_belone = #{sysBelone}")
    User getUserByUserName(
        @Param("userName") String userName, 
        @Param("sysBelone") String sysBelone
    );
}