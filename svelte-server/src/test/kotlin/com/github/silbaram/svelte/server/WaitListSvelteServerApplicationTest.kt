package com.github.silbaram.svelte.server

import com.github.silbaram.svelte.server.annotation.EnableNettyServer
import com.github.silbaram.svelte.server.configuration.WaitListNettyServerConfigurationTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import kotlin.system.exitProcess

@SpringBootTest
@Profile("local")
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
            for (i in 1..5) {
                val bootstrap = Bootstrap()
                bootstrap.group(clientGroup)
                    .channel(NioSocketChannel::class.java)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            val pipeline = ch.pipeline()
                            pipeline.addLast(LoggingHandler(LogLevel.INFO))
                            pipeline.addLast(StringDecoder())
                            pipeline.addLast(StringEncoder())
                            pipeline.addLast(object : SimpleChannelInboundHandler<String>() {
                                override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
                                    println("서버로부터 수신한 메시지: $msg")
                                }
                            })
                        }
                    })

                // 서버에 접속 시도
                val channelFuture = bootstrap.connect(host, port).sync()

                // 접속 성공 여부 확인
                channelFuture.isSuccess shouldBe true

                // 일정 시간 대기하여 서버가 연결을 처리할 시간을 줌
                Thread.sleep(100)
            }
            // 모든 클라이언트가 접속한 후, 대기 시간 동안 서버로부터 메시지를 수신할 수 있도록 추가 대기
            Thread.sleep(11000)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clientGroup.shutdownGracefully()
        }
    }

    afterTest {
        exitProcess(0)
    }
})