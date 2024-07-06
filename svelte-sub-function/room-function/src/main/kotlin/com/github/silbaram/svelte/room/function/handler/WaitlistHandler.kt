package com.github.silbaram.svelte.room.function.handler

import com.github.silbaram.svelte.domain.user.User
import com.github.silbaram.svelte.room.function.queue.context.WaitlistQueueContext
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.LoggerFactory

class WaitlistHandler(
    private val waitlistQueueContext: WaitlistQueueContext
): ChannelInboundHandlerAdapter() {
    private val logger = LoggerFactory.getLogger(WaitlistHandler::class.java)

    override fun channelActive(ctx: ChannelHandlerContext) {
        val user = getUserFromContext(ctx)
        waitlistQueueContext.addStandbyUser(user)
        logger.info("사용자가 대기열에 추가되었습니다: ${user.channelId}, ${user.channel}")
        super.channelActive(ctx)
    }

    private fun getUserFromContext(ctx: ChannelHandlerContext): User {
        return User(
            channelId = ctx.channel().id(),
            channel = ctx.channel(),
            username = "",
        )
    }
}