package com.example.auth.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface AdminMapper {
    
    /**
     * 创建用户封禁记录
     * @param account 用户ID
     * @param duration 封禁时长（小时）
     * @param startTime 封禁开始时间
     * @return 影响行数
     */
    @Insert("INSERT INTO forbid (account, last, start_time, is_delete) " +
            "VALUES (#{account}, #{duration}, #{startTime}, 0)")
    int createForbid(
        @Param("account") String account,
        @Param("duration") Integer duration,
        @Param("startTime") LocalDateTime startTime
    );

    /**
     * 解除用户封禁（软删除）
     * @param account 用户ID
     * @return 影响行数
     */
    @Update("UPDATE forbid SET is_delete = 1 " +
            "WHERE account = #{account} AND is_delete = 0")
    int removeForbid(@Param("account") String account);

    /**
     * 修改封禁时长
     * @param account 用户ID
     * @param duration 新的封禁时长（小时）
     * @return 影响行数
     */
    @Update("UPDATE forbid SET last = #{duration} " +
            "WHERE account = #{account} AND is_delete = 0")
    int updateForbidDuration(
        @Param("account") String account,
        @Param("duration") Integer duration
    );
}
