package com.github.silbaram.svelte.server

import com.github.silbaram.infrastructures.NetworkScan
import com.github.silbaram.infrastructures.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.NettyServerConfiguration
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@EnableNettyServer(nettyServerConfiguration = NettyServerConfiguration::class)
@ComponentScan(basePackageClasses = [SvelteServerScan::class, NetworkScan::class])
class SvelteServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SvelteServerApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}