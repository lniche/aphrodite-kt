# Aphrodite

Aphrodite 是一个基于 kotlin,springboot3,gradle 开发的模板项目，旨在为 能够快速上手开发，理解框架使用流程，提供容器部署模板。

## 特性

- [特性1：描述]
- [特性2：描述]
- [特性3：描述]

| 技术                  | 版本      | 说明                                |
|---------------------|---------|-----------------------------------|
| Spring Boot         | 3.0.4   | MVC核心框架                           |
| Spring Security web | 3.0.4   | web应用安全防护                         |
| satoken             | 1.34.0  | 一个轻量级 Java 权限认证框架，取代spring oauth2 |
| MyBatis             | 3.5.10  | ORM框架                             |
| MyBatisPlus         | 3.5.3.1 | 基于mybatis，使用lambda表达式的            |
| spring-doc          | 2.0.0   | 接口文档工具                            |
| jakarta-validation  | 3.0.2   | 验证框架                              |
| redisson            | 3.19.3  | 对redis进行封装、集成分布式锁等                |
| hikari              | 5.0.1   | 数据库连接池                            |
| logback             | 1.4.5   | log日志工具                           |
| lombok              | 1.18.26 | 简化对象封装工具                          |
| hutool              | 5.8.15  | 更适合国人的java工具集                     |
| knife4j             | 4.0.0   | 基于swagger，更便于国人使用的swagger ui      |

## 安装

首先，确保你已经安装了 JDK21 语言环境。然后，可以通过以下步骤安装 Aphrodite：

```bash
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite
./gradlew build
./gradlew run
