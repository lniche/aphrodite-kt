# Aphrodite Ktor API Scaffold

[English](README.md) | [简体中文](README-zh.md) | [日本語](README-jp.md)

Aphrodite is a template project based on Ktor, designed to help developers get started
quickly and gain a deep understanding of the framework's usage process. The project provides comprehensive sample code
and configuration, covering common development scenarios for easy learning and practice. In addition, Aphrodite also
includes a container deployment template, which makes the project easy to deploy and manage in a modern cloud
environment, helping developers to efficiently build and release applications.

## Tech Stack

| Technology                                                                           | Description                                     |
|--------------------------------------------------------------------------------------|-------------------------------------------------|
| [ktor-server-default-headers-jvm](https://ktor.io/docs/default-headers.html)         | Ktor server default headers support             |
| [ktor-server-call-logging-jvm](https://ktor.io/docs/call-logging.html)               | Ktor server call logging                        |
| [ktor-server-call-id-jvm](https://ktor.io/docs/call-id.html)                         | Ktor server call ID generation                  |
| [ktor-server-rate-limiting-jvm](https://github.com/flaxoos/ktor-rate-limiting)       | Ktor server rate limiting                       |
| [ktor-server-core-jvm](https://ktor.io/docs/core.html)                               | Ktor core server features                       |
| [ktor-serialization-kotlinx-json-jvm](https://ktor.io/docs/serialization.html)       | Ktor serialization using kotlinx-json           |
| [ktor-server-content-negotiation-jvm](https://ktor.io/docs/content-negotiation.html) | Ktor content negotiation                        |
| [postgresql](https://jdbc.postgresql.org/)                                           | PostgreSQL database driver                      |
| [ktor-server-host-common-jvm](https://ktor.io/docs/host-common.html)                 | Ktor common host utilities                      |
| [ktor-server-status-pages-jvm](https://ktor.io/docs/status-pages.html)               | Ktor server status pages handling               |
| [ktor-server-swagger-jvm](https://ktor.io/docs/openapi.html)                         | Ktor Swagger support                            |
| [ktor-server-openapi](https://ktor.io/docs/openapi.html)                             | Ktor OpenAPI support                            |
| [ktor-server-conditional-headers-jvm](https://ktor.io/docs/conditional-headers.html) | Ktor conditional headers support                |
| [ktor-server-cors-jvm](https://ktor.io/docs/cors.html)                               | Ktor Cross-Origin Resource Sharing (CORS)       |
| [ktor-server-compression-jvm](https://ktor.io/docs/compression.html)                 | Ktor response compression                       |
| [ktor-server-auth-jwt-jvm](https://ktor.io/docs/jwt.html)                            | Ktor JWT-based authentication                   |
| [ktor-server-netty-jvm](https://ktor.io/docs/netty.html)                             | Netty engine support for Ktor server            |
| [exposed-core](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | Core module of Exposed, Kotlin SQL framework    |
| [exposed-jdbc](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | JDBC module of Exposed                          |
| [exposed-dao](https://github.com/JetBrains/Exposed/wiki/DataAccessObjects)           | DAO module of Exposed for ORM-style data access |
| [exposed-kotlin-datetime](https://github.com/JetBrains/Exposed/wiki/Getting-Started) | Kotlin datetime integration for Exposed         |
| [logback-classic](https://logback.qos.ch/manual/classic.html)                        | Logback classic library for logging             |
| [lettuce-core](https://lettuce.io/core/release/reference/)                           | Reactive Redis client library                   |
| [kotlin-test-junit](https://kotlinlang.org/docs/junit-5.html)                        | Kotlin test support for JUnit                   |

## Features

- **User authentication and authorization**: Provides basic user login and permission authorization functions.
- **Distributed lock**: Distributed lock based on Redis to ensure resource security in a distributed environment.
- **Middleware support**: Built-in commonly used middleware, including authentication, request logs, cross-domain
  processing, etc.
- **Unified output format**: Provides a simple and easy-to-use API Result unified output method, standardizes the API
  response format, and improves interface consistency.
- **API modular design**: Supports modular API design, which is easy to expand and maintain.
- **Swagger document integration**: Automatically generates API documents for front-end development and testing.

## Structure

```
.
├── scripts/
├── database/
├── deploy/
├── docs/
├── src/
│ ├── common/
│ ├── model/
│ ├── plugin/
│ ├── route/
│ ├── service/
│ ├── utils/
├── storage/
└── README.md

```

## Run Local

```bash
# 1. Clone the project code base
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite-kt

# 2. Configuration file
edit application.conf

# 3. Handle dependencies
# Make sure you have JDK21 installed
./gradlew build

# 4. Initialize the database
database/init.sql

# 5. Start the service
./gradlew run
```

## Repo Activity

![Alt](https://repobeats.axiom.co/api/embed/8c4c3c37cf3d00a71bc527b1a0eee18d2f20f7b5.svg "Repobeats analytics image")

## License

This project is licensed under the MIT License.

## Acknowledgements

Special thanks to all contributors and supporters, your help is essential to us!
