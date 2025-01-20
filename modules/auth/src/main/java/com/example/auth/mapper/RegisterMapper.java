package com.example.auth.mapper;

import com.example.auth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {
    
    @Select("SELECT COUNT(*) FROM user_info WHERE account = #{account} AND sys_belone = #{sysBelone}")
    int checkAccountExists(String account, String sysBelone);
    
    @Select("SELECT COUNT(*) FROM user_info WHERE username = #{username} AND sys_belone = #{sysBelone}")
    int checkUsernameExists(String username, String sysBelone);
    
    @Select("SELECT COUNT(*) FROM user_info WHERE email = #{email} AND sys_belone = #{sysBelone}")
    int checkEmailExists(String email, String sysBelone);
    
    @Insert("INSERT INTO user_info (account, username, password, email, sys_belone, phone, sexual, avatar, sign) " +
            "VALUES (#{account}, #{username}, #{password}, #{email}, #{sysBelone}, #{phone}, #{sexual}, #{avatar}, #{sign})")
    int insertUser(User user);
    
    /**
     * 统计指定系统的用户总数
     * @param sysBelone 所属系统
     * @return 用户总数
     */
    @Select("SELECT COUNT(*) FROM user_info WHERE sys_belone = #{sysBelone}")
    int countUserNumber(String sysBelone);
} 