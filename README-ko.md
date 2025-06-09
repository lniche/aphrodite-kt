# Aphrodite Ktor API Scaffold

[English](README.md) | [简体中文](README-zh.md) | [日本語](README-ja.md) | [한국어](README-ko.md)

Aphrodite는 Ktor 기반의 템플릿 프로젝트로, 개발자들이 빠르게 시작하고 프레임워크의 사용 과정을 깊이 이해할 수 있도록 설계되었습니다. 이 프로젝트는 다양한 개발 시나리오를 아우르는 포괄적인 샘플 코드와
설정을 제공하여 학습과 실습이 용이합니다. 또한, Aphrodite는 컨테이너 배포 템플릿도 포함하고 있어 현대적인 클라우드 환경에서 손쉽게 배포 및 관리할 수 있으며, 개발자들이 효율적으로 애플리케이션을 구축하고
릴리스할 수 있도록 지원합니다.

## 기술 스택

| 기술                                                                                   | 설명                                |
|--------------------------------------------------------------------------------------|-----------------------------------|
| [ktor-server-default-headers-jvm](https://ktor.io/docs/default-headers.html)         | Ktor 서버 기본 헤더 지원                  |
| [ktor-server-call-logging-jvm](https://ktor.io/docs/call-logging.html)               | Ktor 서버 호출 로깅                     |
| [ktor-server-call-id-jvm](https://ktor.io/docs/call-id.html)                         | Ktor 서버 호출 ID 생성                  |
| [ktor-server-rate-limiting-jvm](https://github.com/flaxoos/ktor-rate-limiting)       | Ktor 서버 속도 제한                     |
| [ktor-server-core-jvm](https://ktor.io/docs/core.html)                               | Ktor 코어 서버 기능                     |
| [ktor-serialization-kotlinx-json-jvm](https://ktor.io/docs/serialization.html)       | kotlinx-json를 이용한 Ktor 직렬화        |
| [ktor-server-content-negotiation-jvm](https://ktor.io/docs/content-negotiation.html) | Ktor 콘텐츠 협상                       |
| [postgresql](https://jdbc.postgresql.org/)                                           | PostgreSQL 데이터베이스 드라이버            |
| [ktor-server-host-common-jvm](https://ktor.io/docs/host-common.html)                 | Ktor 공통 호스트 유틸리티                  |
| [ktor-server-status-pages-jvm](https://ktor.io/docs/status-pages.html)               | Ktor 서버 상태 페이지 처리                 |
| [ktor-server-swagger-jvm](https://ktor.io/docs/openapi.html)                         | Ktor Swagger 지원                   |
| [ktor-server-openapi](https://ktor.io/docs/openapi.html)                             | Ktor OpenAPI 지원                   |
| [ktor-server-conditional-headers-jvm](https://ktor.io/docs/conditional-headers.html) | Ktor 조건부 헤더 지원                    |
| [ktor-server-cors-jvm](https://ktor.io/docs/cors.html)                               | Ktor 교차 출처 리소스 공유(CORS)           |
| [ktor-server-compression-jvm](https://ktor.io/docs/compression.html)                 | Ktor 응답 압축                        |
| [ktor-server-auth-jwt-jvm](https://ktor.io/docs/jwt.html)                            | Ktor JWT 기반 인증                    |
| [ktor-server-netty-jvm](https://ktor.io/docs/netty.html)                             | Ktor 서버용 Netty 엔진 지원              |
| [exposed-core](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | Exposed 코어 모듈, Kotlin SQL 프레임워크   |
| [exposed-jdbc](https://github.com/JetBrains/Exposed/wiki/Getting-Started)            | Exposed JDBC 모듈                   |
| [exposed-dao](https://github.com/JetBrains/Exposed/wiki/DataAccessObjects)           | ORM 스타일 데이터 접근을 위한 Exposed DAO 모듈 |
| [exposed-kotlin-datetime](https://github.com/JetBrains/Exposed/wiki/Getting-Started) | Exposed용 Kotlin 날짜 시간 통합          |
| [logback-classic](https://logback.qos.ch/manual/classic.html)                        | 로깅용 Logback 클래식 라이브러리             |
| [lettuce-core](https://lettuce.io/core/release/reference/)                           | 반응형 Redis 클라이언트 라이브러리             |
| [kotlin-test-junit](https://kotlinlang.org/docs/junit-5.html)                        | JUnit용 Kotlin 테스트 지원              |

## 주요 기능

- **사용자 인증 및 권한 부여**: 기본적인 사용자 로그인 및 권한 인증 기능을 제공합니다.
- **분산 락**: Redis 기반의 분산 락을 통해 분산 환경에서 자원의 안전성을 보장합니다.
- **미들웨어 지원**: 인증, 요청 로그, 크로스 도메인 처리 등 자주 사용하는 미들웨어가 내장되어 있습니다.
- **통합 출력 포맷**: 간단하고 사용하기 쉬운 API 결과 통합 출력 방식을 제공하여 API 응답 형식을 표준화하고 인터페이스 일관성을 향상시킵니다.
- **API 모듈화 설계**: 확장과 유지보수가 용이한 모듈화된 API 설계를 지원합니다.
- **Swagger 문서 통합**: 프론트엔드 개발 및 테스트를 위한 API 문서를 자동으로 생성합니다.

## 구조

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

## 로컬 실행

```bash
# 1. 프로젝트 코드 베이스 클론
git clone https://github.com/lniche/aphrodite-kt.git
cd aphrodite-kt

# 2. 설정 파일 편집
application.conf 수정

# 3. 의존성 처리
# JDK21이 설치되어 있는지 확인하세요
./gradlew build

# 4. 데이터베이스 초기화
database.migrations/V1.0.0__initial_schema.sql 실행

# 5. 서비스 시작
./gradlew run
```

## 저장소 활동

![Alt](https://repobeats.axiom.co/api/embed/8c4c3c37cf3d00a71bc527b1a0eee18d2f20f7b5.svg "Repobeats analytics image")

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 감사의 말씀

모든 기여자와 후원자분들께 특별한 감사를 드립니다. 여러분의 도움이 저희에게 큰 힘이 됩니다!
