# Aphrodite

Aphrodite 是一个基于 Kotlin、Spring Boot 3 和 Gradle
开发的模板项目，旨在帮助开发者快速上手，深入理解框架的使用流程。该项目提供了全面的示例代码和配置，涵盖了常见的开发场景，以便于学习和实践。此外，Aphrodite
还包含容器部署模板，使得项目在现代云环境中能够轻松部署与管理，助力开发者高效构建和发布应用。
## 特性

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

| 功能     | 说明                       |
|--------|--------------------------|
| 登录授权功能 | 提供基础的用户登录和权限授权功能         |
| 分布式锁   | 基于 Redis 实现的分布式锁         |
| 中间件    | 包含认证、请求日志、跨域中间件          |
| 实用封装   | 提供 AES、Hash、时间格式化等常用工具封装 |
| 统一输出方式 | 简单易用的 API Result 统一输出方式  |

## 模块说明

- app => 应用模块
- pkg => 公共模块

## 本地运行

首先，确保你已经安装了 JDK21 语言环境。然后，可以通过以下步骤安装 Aphrodite：

```bash
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite
./gradlew build
./gradlew run
