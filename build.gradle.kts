import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
//로컬에 배포하기용
//    id("java-library")
//    id("maven-publish")

    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23" apply false ////Kotlin 으로 작성된 Spring Boot 효과적으로 개발할 수 있게 해주는 설정을 자동으로 추가
    kotlin("plugin.serialization") version "1.9.23" apply false //Kotlin Serialization 을 사용하기 위한 설정을 자동으로 추가
}

//로컬에 배포하기용
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            groupId = "io.github.silbaram"
//            artifactId = "svelte-game-server-engine"
//            version = "0.0.1-SNAPSHOT"
//
//            from(components["java"])
//        }
//    }
//}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

allprojects {
    group = "io.github.silbaram"
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

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
        //https://kotest.io/docs/framework/project-setup.html
        testImplementation("io.kotest:kotest-runner-junit5:5.8.1")

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(subprojects.map { it.tasks.named("compileKotlin") })
    from(subprojects.map { it.tasks.named("processResources") })
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

project(":svelte-domain") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}

project(":infrastructures") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = false
}

project(":svelte-sub-function") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = false
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

project(":svelte-sub-function:room-function") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":svelte-domain"))
    }
}

project(":svelte-server") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":infrastructures:network"))
        implementation(project(":svelte-sub-function:room-function"))
    }
}