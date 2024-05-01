package com.github.silbaram.svelte.server

import com.github.silbaram.svelte.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.NettyServerConfigurationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.SpringBootTest
import kotlin.system.exitProcess

@SpringBootTest
@EnableNettyServer(serverConfigurationClasses = [NettyServerConfigurationTest::class])
class SvelteServerApplicationTest: FunSpec({

    test("main starts the application") {
        val context = SpringApplicationBuilder(SvelteServerApplicationTest::class.java)
            .web(WebApplicationType.NONE)
            .run()

        context.isRunning shouldBe true
        exitProcess(0)
    }
})