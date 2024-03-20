package com.github.silbaram.infrastructures.socket.annotation

import com.github.silbaram.infrastructures.socket.configuration.NettyServerConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(NettyServerConfiguration::class)
annotation class EnableTcpServer
