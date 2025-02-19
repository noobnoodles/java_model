package com.example.auth.mapper;

import com.example.auth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {
    
    @Select("SELECT COUNT(*) FROM user_info WHERE account = #{account} ")
    int checkAccountExists(String account);
    
    @Select("SELECT COUNT(*) FROM user_info WHERE username = #{username} ")
    int checkUsernameExists(String username);
    
    @Select("SELECT COUNT(*) FROM user_info WHERE email = #{email}")
    int checkEmailExists(String email);
    
    @Insert("INSERT INTO user_info (account, username, password, email, phone, sexual, avatar, sign) " +
            "VALUES (#{account}, #{username}, #{password}, #{email}, #{phone}, #{sexual}, #{avatar}, #{sign})")
    int insertUser(User user);
    
    /**
     * 统计指定系统的用户总数
     * @param sysBelone 所属系统
     * @return 用户总数
     */
    @Select("SELECT COUNT(*) FROM user_info")
    int countUserNumber();
} 