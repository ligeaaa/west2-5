# 像素市场 - 后端文档

- 前端仓库：*https://github.com/KOAA78/pixel-market*

- 接口文档：*https://console-docs.apipost.cn/preview/bc0599b21fed61ef/478aacc123476389?target_id=95afa8ad-94e7-4215-a901-2a64d89b0f97*

#### 技术栈

*Springboot + Mysql + Mybatis-Plus + Shiro+ Redis*

#### 完成情况

- 基本交易功能 ✅
- 后台处理举报功能 ✅

#### 项目亮点

- **封装情况**
    - 实现统一的返回前端数据格式，提高了代码的一致性。
    - 引入自定义异常码，实现统一异常拦截，提高了定位异常所在的速度。
    - 根据不同的请求和响应需求，使用新的类进行处理，提高了代码的模块化。
    - 引入通用常量，避免了在代码中使用没有明确含义的数字，提高了代码的可维护性。
- **安全性能**
    - 使用Shiro框架实现了用户密码的加盐加密存储，加强了用户数据的安全性。
    - 开启接口鉴权，确保只有经过认证的用户能够访问相应的接口，提升了系统防护能力。
    - 使用雪花算法生成数据表所需的唯一标识Id，保证了数据的唯一性和完整性。

#### 未解决的问题

- 项目部署上线（但Mysql数据库已经实现远程服务器）
- 如何持久化保存Shiro会话，以便在每次重启后仍然保持登录状态，而不需要每次都通过登录接口获取新的session。
- 聊天功能 ❓ （前后端都尝试写了代码，但没成功连接起来）

#### 项目目录树

```
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─west2_5
│  │  │          ├─common
│  │  │          ├─config
│  │  │          │  └─shiro
│  │  │          ├─constants
│  │  │          ├─controller
│  │  │          ├─exception
│  │  │          ├─mapper
│  │  │          ├─model
│  │  │          │  ├─entity
│  │  │          │  ├─request
│  │  │          │  └─response
│  │  │          ├─service
│  │  │          │  └─impl
│  │  │          └─utils
│  │  └─resources
```

