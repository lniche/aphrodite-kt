plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.lombok") version "2.0.20"
    id("io.freefair.lombok") version "8.10"
}

group = "top.threshold"
version = "1.0.0"
description = "Aphrodite Spring Boot 3 API Scaffold"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.redisson:redisson-spring-boot-starter:3.36.0")
    implementation("com.alibaba:transmittable-thread-local:2.14.5")
    implementation("cn.hutool:hutool-all:5.8.32")
    implementation("org.apache.commons:commons-pool2")
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.7")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("io.jsonwebtoken:jjwt:0.12.6")
    implementation("org.projectlombok:lombok")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    "developmentOnly"("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-freemarker")
    testImplementation("com.baomidou:mybatis-plus-generator:3.5.7")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
