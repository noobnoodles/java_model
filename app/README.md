# App Module

应用程序主模块，负责整合各个功能模块并提供完整的应用服务。

## 功能特点

- 集成认证授权
- 提供 Web API 接口
- 数据库访问
- 缓存支持
- API 文档

## 技术栈

- Spring Boot
- MyBatis Plus
- Redis
- MySQL
- Knife4j

## 目录结构

```
app/
├── src/main/java/
│   └── com/example/app/
│       ├── config/      # 配置类
│       ├── controller/  # 控制器
│       ├── service/     # 服务层
│       └── mapper/      # 数据访问层
└── src/main/resources/
    ├── mapper/         # MyBatis 映射文件
    └── application.yml # 配置文件
``` 