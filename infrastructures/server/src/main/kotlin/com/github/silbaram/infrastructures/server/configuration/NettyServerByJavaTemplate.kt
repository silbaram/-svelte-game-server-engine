package com.github.silbaram.infrastructures.server.configuration

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import java.util.concurrent.ThreadFactory


abstract class NettyServerByJavaTemplate: NettyServer {

    fun nettyServerStart() {
        val logger = LoggerFactory.getLogger(NettyServerByJavaTemplate::class.java)
        logger.info("Svelte Engine started on port : ${serverPort()}")

        val bossGroup: EventLoopGroup = createBossGroup()
        val workerGroup: EventLoopGroup = createWorkerGroup()

        try {
            val serverBootstrap = createServerBootstrap()
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
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

    private fun createServerBootstrap(): ServerBootstrap = ServerBootstrap()

    fun threadFactory(threadName: String): ThreadFactory = Thread.ofVirtual().name(threadName).factory()

    @Bean(destroyMethod = "shutdownGracefully")
    private fun createBossGroup(): EventLoopGroup = NioEventLoopGroup()

    @Bean(destroyMethod = "shutdownGracefully")
    abstract fun createWorkerGroup(): EventLoopGroup

    abstract fun addHandler(socketChannel: SocketChannel)

    abstract fun serverBootstrapAddOption(serverBootstrap: ServerBootstrap)

    abstract fun serverPort(): Int
}