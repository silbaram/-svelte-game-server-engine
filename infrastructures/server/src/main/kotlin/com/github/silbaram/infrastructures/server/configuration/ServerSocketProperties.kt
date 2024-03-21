package com.github.silbaram.infrastructures.server.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "svelte-app.server.socket")
data class ServerSocketProperties(
    val port: Int = 8089,
    val workerGroupThreadCount: Int = 10
)