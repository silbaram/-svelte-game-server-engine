package com.github.silbaram.svelte.server

import com.github.silbaram.infrastructures.socket.annotation.EnableTcpServer
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@EnableTcpServer
@SpringBootApplication
class SvelteServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SvelteServerApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}