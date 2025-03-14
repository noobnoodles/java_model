package com.example.auth.mapper;

import com.example.auth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {
    
    @Select("SELECT * FROM user_info WHERE account = #{account}")
    User getUserByAccount(
        @Param("account") String account
    );
    
    @Select("SELECT * FROM user_info WHERE phone = #{phone}")
    User getUserByPhone(
        @Param("phone") String phone
    );
    
    @Select("SELECT * FROM user_info WHERE email = #{email}")
    User getUserByEmail(
        @Param("email") String email
    );

    @Select("SELECT * FROM user_info WHERE username = #{userName}")
    User getUserByUserName(
        @Param("userName") String userName
    );

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @Select("SELECT * FROM user_info")
    List<User> getAllUsers();
}