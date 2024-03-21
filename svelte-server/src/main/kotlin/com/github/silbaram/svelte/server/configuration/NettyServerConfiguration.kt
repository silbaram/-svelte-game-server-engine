package com.github.silbaram.svelte.server.configuration

import com.github.silbaram.infrastructures.server.configuration.EchoHandler
import com.github.silbaram.infrastructures.server.configuration.NettyServerConfigurationTemplate
import com.github.silbaram.infrastructures.server.configuration.ServerSocketProperties
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

class NettyServerConfiguration(
    private val serverSocketProperties: ServerSocketProperties
): NettyServerConfigurationTemplate() {

    @Throws(Exception::class)
    override fun test() {
        val bossGroup: EventLoopGroup = NioEventLoopGroup()
        val workerGroup: EventLoopGroup = NioEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * serverSocketProperties.workerGroupThreadCount,
            Thread.ofVirtual().name("workerGroupVirtualThread").factory()
        )

        try {
            val serverBootstrap = ServerBootstrap()

            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    @Throws(Exception::class)
                    public override fun initChannel(ch: SocketChannel) {
                        ch.pipeline().addLast(EchoHandler())
                    }
                })
                /*
                    코드는 서버가 수용할 수 있는 대기 중인 연결 요청의 최대 큐 크기를 설정합니다. SO_BACKLOG는 TCP/IP 서버에서 사용되는 옵션으로,
                    서버가 아직 수락하지 않은 클라이언트 연결 요청의 최대 큐 크기를 설정합니다. 여기서는 큐의 크기를 128로 설정하였습니다.
                    이는 동시에 128개의 연결 요청을 대기할 수 있음을 의미합니다.
                 */
                .option(ChannelOption.SO_BACKLOG, 128)
                /*
                    코드는 연결이 유휴 상태일 때도 TCP/IP 프로토콜이 연결을 유지하도록 설정합니다. SO_KEEPALIVE 옵션을 true로 설정하면,
                    클라이언트와 서버 간의 연결이 끊어졌는지 확인하기 위해 주기적으로 heartbeat 메시지를 보냅니다.
                    이를 통해 불필요한 연결을 빠르게 감지하고 종료할 수 있습니다.
                 */
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            val f = serverBootstrap.bind(serverSocketProperties.port).sync()
            f.channel().closeFuture().sync()
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}