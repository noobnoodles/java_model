# Common Module

公共功能模块，提供各个模块共用的工具类和通用功能。

## 子模块

### common-core
- 核心工具类
- 通用实体类
- 异常处理
- 常量定义

### common-redis
- Redis 操作封装
- 缓存管理
- 分布式锁

### common-security
- 安全工具类
- 加密解密
- 权限验证

## 使用方式

在需要使用的模块的 pom.xml 中添加依赖：
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>
``` 