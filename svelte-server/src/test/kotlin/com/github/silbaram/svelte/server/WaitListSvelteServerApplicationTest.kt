package com.github.silbaram.svelte.server

import com.github.silbaram.svelte.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.WaitListNettyServerConfigurationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.SpringBootTest
import kotlin.system.exitProcess

@SpringBootTest
@EnableNettyServer(serverConfigurationClasses = [WaitListNettyServerConfigurationTest::class])
class WaitListSvelteServerApplicationTest: FunSpec({

    beforeSpec {
        SpringApplicationBuilder(WaitListSvelteServerApplicationTest::class.java)
            .web(WebApplicationType.NONE)
            .run()

        Thread.sleep(1000)
    }

    test("사용자를 대기 큐에 등록 테스트") {
        val host = "localhost"
        val port = 22000 // 서버 포트 번호
        val clientGroup = NioEventLoopGroup()

        try {
            val bootstrap = Bootstrap()
            bootstrap.group(clientGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        ch.pipeline().addLast(LoggingHandler(LogLevel.INFO))
                    }
                })

            // 서버에 접속 시도
            val channelFuture = bootstrap.connect(host, port).sync()

            // 접속 성공 여부 확인
            channelFuture.isSuccess shouldBe true

            // 접속 해제
            channelFuture.channel().closeFuture().sync()
        } finally {
            clientGroup.shutdownGracefully()
        }
    }

    afterTest {
        exitProcess(0)
    }
})