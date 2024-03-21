import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"

    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22" apply false ////Kotlin 으로 작성된 Spring Boot 효과적으로 개발할 수 있게 해주는 설정을 자동으로 추가
    kotlin("plugin.serialization") version "1.9.22" apply false //Kotlin Serialization 을 사용하기 위한 설정을 자동으로 추가
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

allprojects {
    group = "com.github.silbaram"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        //kotlin 의존성
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // kotlinx-coroutines 의존성
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.2")

        testImplementation("io.projectreactor:reactor-test")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

project(":infrastructures") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}

project(":infrastructures:network") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation("io.netty:netty-all:4.1.107.Final")
    }
}

project(":infrastructures:server") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation("io.netty:netty-all:4.1.107.Final")
    }
}

project(":svelte-server") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":infrastructures:network"))
        implementation(project(":infrastructures:server"))
    }
}