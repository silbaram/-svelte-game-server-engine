package com.github.silbaram.svelte.server.configuration

import com.github.silbaram.infrastructures.server.configuration.NettyServerTemplate
import com.github.silbaram.svelte.server.handler.EchoHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel

open class NettyServerConfiguration: NettyServerTemplate() {

    override fun createWorkerGroup(): EventLoopGroup {
        return NioEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * 10,
            threadFactory("workerGroupVirtualThread")
        )
    }

    override fun addHandler(socketChannel: SocketChannel) {
        socketChannel.pipeline().addLast(EchoHandler())
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