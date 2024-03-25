package com.github.silbaram.svelte.server.handler

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter


//TODO : 이 클래스는 테스트를 위해 만들어진 클래스입니다. 추후 삭제될 예정입니다.
class EchoHandler: ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println(Thread.currentThread().isVirtual)
        println(Thread.currentThread().name)
        ctx.write(msg)
        ctx.flush()
    }
}