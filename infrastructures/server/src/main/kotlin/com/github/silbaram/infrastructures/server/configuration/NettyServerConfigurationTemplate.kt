package com.github.silbaram.infrastructures.server.configuration

import org.springframework.context.annotation.Bean

open class NettyServerConfigurationTemplate {

    @Bean
    fun nettyServerRun() {
        test()
    }

    open fun test() {}
}