plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("kapt") version "2.0.20"
    kotlin("plugin.allopen") version "2.0.20"
    kotlin("plugin.noarg") version "2.0.20"
    kotlin("plugin.lombok") version "2.0.20"
    id("io.freefair.lombok") version "8.10"
}

group = "top.threshold"
version = "1.0.0"

noArg {

}

allOpen {

}

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
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    "developmentOnly"("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("com.alibaba:transmittable-thread-local:2.14.5")
    implementation("cn.hutool:hutool-all:5.8.32")
    implementation("cn.dev33:sa-token-spring-boot3-starter:1.39.0")
    implementation("cn.dev33:sa-token-redis-jackson:1.39.0")
    implementation("org.apache.commons:commons-pool2")
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.7")
    implementation("com.baomidou:mybatis-plus-generator:3.5.7")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
