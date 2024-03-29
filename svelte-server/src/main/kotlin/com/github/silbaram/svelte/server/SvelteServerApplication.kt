package com.github.silbaram.svelte.server

import com.github.silbaram.svelte.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.test.NettyServerConfiguration
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

//TODO 테스트 코드 이므로 최종엔 삭제 해야됨
@SpringBootApplication
@EnableNettyServer(serverConfigurationClasses = [NettyServerConfiguration::class])
class SvelteServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SvelteServerApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)

}