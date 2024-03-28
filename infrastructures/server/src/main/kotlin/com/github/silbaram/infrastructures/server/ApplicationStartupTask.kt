package com.github.silbaram.infrastructures.server

import com.github.silbaram.infrastructures.server.configuration.NettyServerByJavaTemplate
import com.github.silbaram.infrastructures.server.configuration.NettyServerByKotlinTemplate
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupTask(
    private val nettyServeryKotlinConfigs: Map<String, NettyServerByKotlinTemplate>,
    private val nettyServerByJavaConfigs: Map<String, NettyServerByJavaTemplate>
): ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        nettyServeryKotlinConfigs.values.forEach(NettyServerByKotlinTemplate::nettyServerStart)
        nettyServerByJavaConfigs.values.forEach(NettyServerByJavaTemplate::nettyServerStart)
    }
}