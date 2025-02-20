package com.example.auth.service;

import com.example.auth.model.dto.AdminDTO;
import com.example.auth.model.dto.BanDTO;
import com.example.auth.model.vo.OPVO;
import com.example.auth.model.vo.UserVO;

import java.util.List;

public interface AdminService {
    
    /**
     * 管理员登录
     * @param adminDTO 管理员登录信息
     * @return 用户信息和token
     * throws BusinessException 当用户不是管理员或信息不匹配时抛出异常
     */
    UserVO adminLogin(AdminDTO adminDTO);

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<OPVO> getUsers();

    /**
     * 修改用户状态（封禁/解封）
     * @param banDTO 封禁信息，包含用户ID、状态、封禁时长和开始时间
     * throws BusinessException 当用户不存在时抛出异常
     */
    void toggleUserStatus(BanDTO banDTO);

    /**
     * 重置用户密码
     * @param userid 用户ID
     * throws BusinessException 当用户不存在时抛出异常
     */
    void resetUserPassword(String userid);
}
