package com.github.silbaram.svelte.server.configuration

import com.github.silbaram.svelte.room.function.handler.WaitlistHandler
import com.github.silbaram.svelte.room.function.queue.context.WaitlistQueueContext
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.ServerChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.springframework.beans.factory.annotation.Autowired

open class WaitListNettyServerConfigurationTest(
    @Autowired val waitlistQueueContext: WaitlistQueueContext
): NettyServerTemplate() {

    override fun createWorkerGroup(): EventLoopGroup {
        return NioEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * 10,
            threadFactory("workerGroupVirtualThread")
        )
    }

    override fun createServerBootstrap(): Class<out ServerChannel> {
        return NioServerSocketChannel::class.java
    }

    override fun addHandler(socketChannel: SocketChannel) {
        socketChannel.pipeline()
            .addLast(WaitlistHandler(waitlistQueueContext))
    }

    override fun serverBootstrapAddOption(serverBootstrap: ServerBootstrap) {
        serverBootstrap
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
    }

    override fun serverPort(): Int {
        return 22000
    }
}