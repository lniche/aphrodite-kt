# Aphrodite Kotlin API 快速开发脚手架

Aphrodite 是一个基于 Kotlin、Spring Boot 3 和 Gradle
开发的模板项目，旨在帮助开发者快速上手，深入理解框架的使用流程。该项目提供了全面的示例代码和配置，涵盖了常见的开发场景，以便于学习和实践。此外，Aphrodite
还包含容器部署模板，使得项目在现代云环境中能够轻松部署与管理，助力开发者高效构建和发布应用。

## 技术栈

| 技术                                                                                                                                               | 说明                              |
|--------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------|
| [spring-boot-starter](https://spring.io/projects/spring-boot)                                                                                    | Spring Boot 的基础依赖               |
| [kotlin-reflect](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/)                                                                   | Kotlin 反射库                      |
| [spring-boot-configuration-processor](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-configuration-processor.html) | Spring Boot 配置处理器               |
| [spring-boot-starter-web](https://spring.io/projects/spring-boot)                                                                                | 提供 Web 应用开发所需的依赖                |
| [spring-boot-starter-undertow](https://spring.io/projects/spring-boot)                                                                           | Undertow 嵌入式 Web 服务器            |
| [spring-boot-starter-validation](https://spring.io/projects/spring-boot)                                                                         | 数据验证支持                          |
| [spring-boot-starter-aop](https://spring.io/projects/spring-boot)                                                                                | AOP（面向切面编程）支持                   |
| [spring-boot-starter-data-redis](https://spring.io/projects/spring-data-redis)                                                                   | Redis 数据访问支持                    |
| [spring-boot-starter-freemarker](https://spring.io/projects/spring-boot)                                                                         | Freemarker 模板引擎支持               |
| [lombok](https://projectlombok.org/)                                                                                                             | 简化 Java 对象封装的工具                 |
| [kotlinx-coroutines-core](https://kotlinlang.org/docs/coroutines-overview.html)                                                                  | Kotlin 协程核心库                    |
| [kotlinx-coroutines-reactor](https://kotlinlang.org/docs/coroutines-guide.html#reactor)                                                          | Kotlin 协程与 Reactor 集成           |
| [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using-boot-devtools)                            | 开发时工具，支持热重载                     |
| [postgresql](https://jdbc.postgresql.org/)                                                                                                       | PostgreSQL 数据库驱动                |
| [junit-platform-launcher](https://junit.org/junit5/docs/current/user-guide/#overview-platform-launcher)                                          | JUnit 平台启动器                     |
| [spring-boot-starter-test](https://spring.io/projects/spring-boot)                                                                               | Spring Boot 测试工具                |
| [kotlin-test-junit5](https://kotlinlang.org/docs/junit-5.html)                                                                                   | Kotlin 测试支持 JUnit 5             |
| [screw-core](https://github.com/ban-jie/screw)                                                                                                   | 数据库文档生成工具                       |
| [knife4j-openapi3-jakarta-spring-boot-starter](https://knife4j.github.io/knife4j/)                                                               | OpenAPI 3 支持的文档生成工具             |
| [redisson-spring-boot-starter](https://github.com/redisson/redisson)                                                                             | Redis 的 Spring Boot 启动器         |
| [guava](https://github.com/google/guava)                                                                                                         | Google 提供的核心库，包含集合和缓存等工具        |
| [transmittable-thread-local](https://github.com/alibaba/transmittable-thread-local)                                                              | 支持跨线程传递的线程本地变量                  |
| [hutool-all](https://github.com/dromara/hutool)                                                                                                  | Java 工具库，提供多种常用功能               |
| [sa-token-spring-boot3-starter](https://github.com/dromara/sa-token)                                                                             | 权限认证框架的 Spring Boot 启动器         |
| [sa-token-redis-jackson](https://github.com/dromara/sa-token)                                                                                    | Sa-Token 的 Redis 和 Jackson 集成支持 |
| [commons-pool2](https://commons.apache.org/proper/commons-pool/)                                                                                 | Apache Commons 连接池              |
| [mybatis-plus-spring-boot3-starter](https://mp.baomidou.com/)                                                                                    | MyBatis 的增强版 Spring Boot 启动器    |
| [mybatis-plus-generator](https://mp.baomidou.com/guide/generator.html)                                                                           | MyBatis-Plus 代码生成器              |

## 特性

- **用户认证与授权**：提供基础的用户登录和权限授权功能。
- **分布式锁**：基于 Redis 实现的分布式锁，保证分布式环境下的资源安全。
- **中间件支持**：内置常用的中间件，包括认证、请求日志、跨域处理等。
- **统一输出格式**：提供简单易用的 API Result 统一输出方式，标准化 API 响应格式，提升接口一致性。
- **API 模块化设计**：支持模块化的 API 设计，易于扩展和维护。
- **Swagger 文档集成**：自动生成 API 文档，便于前端开发和测试。

## 目录结构

```
├── aspect/               # 切面模块，处理横切关注点，如日志、事务管理
├── config/               # 配置模块，存储应用程序的配置和环境设置
├── constant/             # 常量模块，定义应用中使用的常量
├── controller/           # 控制器模块，处理请求和响应的逻辑
├── entity/               # 实体模块，定义与数据库表对应的实体类
├── enums/                # 枚举模块，定义常用的枚举类型
├── exception/            # 异常模块，自定义异常处理和错误管理
├── handler/              # 处理器模块，处理特定请求或事件的逻辑
├── helper/               # 辅助模块，提供一些通用的辅助方法
├── mapper/               # 映射模块，用于对象与数据库之间的映射
├── repository/           # 数据访问层模块，处理数据存取操作，通常与数据库交互
├── service/              # 服务层模块，包含业务逻辑处理，协调不同模块
├── utils/                # 工具类模块，常用工具方法和功能的集合
└── README.md             # 项目说明文件，包含项目简介、安装和使用说明等

```

## 本地运行

```bash
# 1. 克隆项目代码库
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite-kt

# 2. 配置文件
update resources config

# 3. 处理依赖
# 确保你已经安装了 JDK21
./gradlew build

# 4. 初始化数据库
database/init.sql

# 5. 启动服务
./gradlew run
```

## 贡献

如果你有任何建议或想法，欢迎创建 Issue 或直接提交 Pull Request。

1. Fork 这个仓库。
2. 创建一个新的分支：git checkout -b feature/your-feature
3. 提交你的更改：git commit -m 'Add new feature'
4. 推送到你的分支：git push origin feature/your-feature
5. 提交 Pull Request。

## 许可证

该项目遵循 MIT 许可证。

## 鸣谢

特别感谢所有贡献者和支持者，您的帮助对我们至关重要！
