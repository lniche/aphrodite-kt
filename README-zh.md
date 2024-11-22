# Aphrodite Ktor API Scaffold

[English](README.md) | [简体中文](README-zh.md)

Aphrodite 是一个基于 Ktor 开发的模板项目，旨在帮助开发者快速上手，深入理解框架的使用流程。该项目提供了全面的示例代码和配置，涵盖了常见的开发场景，以便于学习和实践。此外，Aphrodite
还包含容器部署模板，使得项目在现代云环境中能够轻松部署与管理，助力开发者高效构建和发布应用。

## 技术栈

| 技术                                                                                   | 说明                              |
|--------------------------------------------------------------------------------------|---------------------------------|
| [ktor-server-default-headers-jvm](https://ktor.io/docs/default-headers.html)         | Ktor 服务默认请求头支持                  |
| [ktor-server-call-logging-jvm](https://ktor.io/docs/call-logging.html)               | Ktor 服务调用日志记录                   |
| [ktor-server-call-id-jvm](https://ktor.io/docs/call-id.html)                         | Ktor 服务调用 ID 生成                 |
| [ktor-server-rate-limiting-jvm](https://github.com/flaxoos/ktor-rate-limiting)       | Ktor 服务限流功能                     |
| [ktor-server-core-jvm](https://ktor.io/docs/core.html)                               | Ktor 核心服务功能                     |
| [ktor-serialization-kotlinx-json-jvm](https://ktor.io/docs/serialization.html)       | 使用 kotlinx-json 的 Ktor 序列化支持    |
| [ktor-server-content-negotiation-jvm](https://ktor.io/docs/content-negotiation.html) | Ktor 内容协商支持                     |
| [postgresql](https://jdbc.postgresql.org/)                                           | PostgreSQL 数据库驱动                |
| [ktor-server-host-common-jvm](https://ktor.io/docs/host-common.html)                 | Ktor 通用主机工具                     |
| [ktor-server-status-pages-jvm](https://ktor.io/docs/status-pages.html)               | Ktor 服务状态页面处理                   |
| [ktor-server-swagger-jvm](https://ktor.io/docs/openapi.html)                         | Ktor Swagger 支持                 |
| [ktor-server-openapi](https://ktor.io/docs/openapi.html)                             | Ktor OpenAPI 支持                 |
| [ktor-server-conditional-headers-jvm](https://ktor.io/docs/conditional-headers.html) | Ktor 条件请求头支持                    |
| [ktor-server-cors-jvm](https://ktor.io/docs/cors.html)                               | Ktor 跨域资源共享（CORS）               |
| [ktor-server-compression-jvm](https://ktor.io/docs/compression.html)                 | Ktor 响应压缩支持                     |
| [ktor-server-auth-jwt-jvm](https://ktor.io/docs/jwt.html)                            | 基于 JWT 的 Ktor 鉴权                |
| [ktor-server-netty-jvm](https://ktor.io/docs/netty.html)                             | Ktor 服务的 Netty 引擎支持             |
| [exposed-core](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | Exposed 的核心模块，Kotlin SQL 框架     |
| [exposed-jdbc](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | Exposed 的 JDBC 模块               |
| [exposed-dao](https://github.com/JetBrains/Exposed/wiki/DataAccessObjects)           | Exposed 的 DAO 模块，支持 ORM 风格的数据访问 |
| [exposed-kotlin-datetime](https://github.com/JetBrains/Exposed/wiki/Getting-Started) | Exposed 对 Kotlin 日期时间的支持        |
| [logback-classic](https://logback.qos.ch/manual/classic.html)                        | Logback Classic 日志库             |
| [lettuce-core](https://lettuce.io/core/release/reference/)                           | 响应式 Redis 客户端库                  |
| [kotlin-test-junit](https://kotlinlang.org/docs/junit-5.html)                        | Kotlin 对 JUnit 的测试支持            |

## 特性

- **用户认证与授权**：提供基础的用户登录和权限授权功能。
- **分布式锁**：基于 Redis 实现的分布式锁，保证分布式环境下的资源安全。
- **中间件支持**：内置常用的中间件，包括认证、请求日志、跨域处理等。
- **统一输出格式**：提供简单易用的 API Result 统一输出方式，标准化 API 响应格式，提升接口一致性。
- **API 模块化设计**：支持模块化的 API 设计，易于扩展和维护。
- **Swagger 文档集成**：自动生成 API 文档，便于前端开发和测试。

## 目录结构

```
.
├── bin/                  # 可执行脚本
├── database/             # 数据库相关
├── deploy/               # 部署相关文件
├── docs/                 # 项目文档
├── src/                  # 核心目录
│   ├── app/              # 应用核心代码
│   ├── pkg/              # 公共模块
├── storage/              # 文件存储
└── README.md             # 项目说明

```

## 本地运行

```bash
# 1. 克隆项目代码库
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite-kt

# 2. 配置文件
edit application.conf

# 3. 处理依赖
# 确保你已经安装了 JDK21
./gradlew build

# 4. 初始化数据库
db/init.sql

# 5. 启动服务
./gradlew run
```

## Repo Activity

![Alt](https://repobeats.axiom.co/api/embed/8c4c3c37cf3d00a71bc527b1a0eee18d2f20f7b5.svg "Repobeats analytics image")

## 贡献

如果你有任何建议或想法，欢迎创建 Issue 或直接提交 Pull Request。

1. Fork 这个仓库。
2. 创建一个新的分支：

```
git checkout -b feature/your-feature
```

3. 提交你的更改：

```
git commit -m 'Add new feature'
```

4. 推送到你的分支：

```
git push origin feature/your-feature
```

5. 提交 Pull Request。

## 许可证

该项目遵循 MIT 许可证。

## 鸣谢

特别感谢所有贡献者和支持者，您的帮助对我们至关重要！
