package com.github.silbaram.svelte.domain.room

import com.github.silbaram.svelte.domain.user.User
import io.netty.channel.group.ChannelGroup
import java.util.UUID

data class Room(
    val roomId: UUID,
    val users: Map<String, User>,
    internal val channelGroup: ChannelGroup
)