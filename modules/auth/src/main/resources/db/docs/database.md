# 数据库设计文档

## 用户信息表(user_info)

### 表结构
| 字段名 | 类型 | 允许空 | 主键 | 说明 |
|--------|------|--------|------|------|
| account | varchar(255) | NO | YES(1) | 账号 |
| username | varchar(255) | NO | | 用户名 |
| email | varchar(255) | YES | | 邮箱 |
| password | varchar(255) | NO | | 密码 |
| sys_belone | varchar(255) | NO | YES(2) | 系统归属 |
| phone | varchar(255) | YES | | 手机号 |
| sexual | smallint | YES | | 性别 |
| avatar | varchar(255) | YES | | 头像 |
| sign | varchar(255) | YES | | 个性签名 |

### 索引说明
- PRIMARY KEY (`account`, `sys_belone`)
- INDEX `idx_username_sys` (`username`, `sys_belone`)
- INDEX `idx_email_sys` (`email`, `sys_belone`)

### 字段说明
- account + sys_belone: 联合主键，确保在同一系统内账号唯一
- username: 用户昵称
- password: 存储加密后的密码
- sys_belone: 用户所属系统
- sexual: 性别(0-女，1-男)
- avatar: 用户头像URL地址
- sign: 用户个性签名

### 关联业务
1. 用户注册
2. 用户登录
3. 密码重置
4. 用户信息管理 