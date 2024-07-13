package com.github.silbaram.svelte.domain.domain_service.room

import com.github.silbaram.svelte.domain.room.Room
import com.github.silbaram.svelte.domain.user.User
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor
import java.util.*

fun List<User>.toRoom(): Room {
    val userByUserId = this.associateBy { it.userId }
    return Room(
        roomId = UUID.randomUUID(),
        users = userByUserId,
        channelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE).apply {
            addAll(userByUserId.values.map { it.channel })
        }
    )
}
    
fun Room.broadcastMessage(message: String) {
    this.channelGroup.writeAndFlush(message)
}