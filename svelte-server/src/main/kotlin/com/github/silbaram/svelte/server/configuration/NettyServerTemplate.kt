package com.github.silbaram.svelte.server.configuration

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.ServerChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Async
import java.util.concurrent.ThreadFactory

abstract class NettyServerTemplate {

    @Async
    @Throws(Exception::class)
    open fun nettyServerStart() {
        val logger = LoggerFactory.getLogger(NettyServerTemplate::class.java)
        logger.info("Svelte Engine started on port : ${serverPort()}")

        val bossGroup: EventLoopGroup = createBossGroup()
        val workerGroup: EventLoopGroup = createWorkerGroup()

        try {
            val serverBootstrap = ServerBootstrap()
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(createServerBootstrap())
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    @Throws(Exception::class)
                    public override fun initChannel(socketChannel: SocketChannel) {
                        addHandler(socketChannel)
                    }
                })
            serverBootstrapAddOption(serverBootstrap)

            val f = serverBootstrap.bind(serverPort()).sync()
            f.channel().closeFuture().sync()
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }

    open fun threadFactory(threadName: String): ThreadFactory = Thread.ofVirtual().name(threadName).factory()

    @Bean(destroyMethod = "shutdownGracefully")
    private fun createBossGroup(): EventLoopGroup = NioEventLoopGroup()

    @Bean(destroyMethod = "shutdownGracefully")
    open fun createWorkerGroup(): EventLoopGroup = NioEventLoopGroup()

    open fun createServerBootstrap(): Class<out ServerChannel> = NioServerSocketChannel::class.java

    abstract fun addHandler(socketChannel: SocketChannel)

    abstract fun serverBootstrapAddOption(serverBootstrap: ServerBootstrap)

    abstract fun serverPort(): Int
}