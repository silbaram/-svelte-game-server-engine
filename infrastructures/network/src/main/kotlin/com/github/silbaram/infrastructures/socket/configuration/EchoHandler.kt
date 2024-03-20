package com.github.silbaram.infrastructures.socket.configuration

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter


class EchoHandler: ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        ctx.write(msg)
        ctx.flush()
    }
}