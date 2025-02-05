# Aphrodite Ktor API Scaffold

[English](README.md) | [简体中文](README-zh.md)

Aphrodite は Spring Boot
3をベースに開発されたテンプレートプロジェクトであり、開発者が素早く始め、フレームワークの使用プロセスを深く理解するのを支援することを目的としています。このプロジェクトでは、一般的な開発シーンを網羅する包括的なサンプルコードと設定が提供されており、学習と実践が容易になります。さらに、Aphroditeにはコンテナデプロイメントテンプレートも含まれているため、プロジェクトは現代のクラウド環境で簡単にデプロイされ、管理することができ、開発者がアプリケーションを効率的に構築し、公開するのを支援します。

## 技術スタック

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

## 特徴

- **ユーザー認証と認可**：基本的なユーザーログインと権限付与機能を提供します。
- **分散ロック**：Redisをベースにした分散ロックを実装し、分散環境下でのリソースの安全性を確保します。
- **ミドルウェアサポート**：認証、リクエストログ、CORS処理など、よく使われるミドルウェアを内蔵しています。
- **統一された出力フォーマット**：簡単に使えるAPI Resultの統一出力方式を提供し、APIのレスポンスフォーマットを標準化して、インターフェースの一貫性を向上させます。
- **APIモジュール設計**：モジュール化されたAPI設計をサポートし、拡張と保守が容易です。
- **Swaggerドキュメント統合**：APIドキュメントを自動生成し、フロントエンド開発とテストを容易にします。

## 目录结构

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

## ローカルでの実行

```bash
# 1. プロジェクトリポジトリをクローン
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite-kt

# 2. 設定ファイルを編集
edit application.conf

# 3. 依存関係を解決
# JDK21がインストールされていることを確認
./mvn clean package

# 4. データベースを初期化
database.migrations/V1.0.0__initial_schema.sql

# 5. サービスを起動
./mvn spring-boot:run
```

## リポジトリの活動

![Alt](https://repobeats.axiom.co/api/embed/8c4c3c37cf3d00a71bc527b1a0eee18d2f20f7b5.svg "Repobeats analytics image")

## ライセンス

このプロジェクトは MIT ライセンスに従っています。

## 感謝の意

すべてのコントリビューターとサポーターに感謝します。皆さんのご支援が私たちにとって非常に重要です！

