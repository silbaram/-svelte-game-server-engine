package com.github.silbaram.svelte.server

import com.github.silbaram.svelte.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.DefaultNettyServerConfigurationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.SpringBootTest
import kotlin.system.exitProcess

@SpringBootTest
@EnableNettyServer(serverConfigurationClasses = [DefaultNettyServerConfigurationTest::class])
class DefaultSvelteServerApplicationTest: FunSpec({

    test("기본 svelte server 기동 테스트") {
        val context = SpringApplicationBuilder(DefaultSvelteServerApplicationTest::class.java)
            .web(WebApplicationType.NONE)
            .run()

        context.isRunning shouldBe true
        exitProcess(0)
    }
})