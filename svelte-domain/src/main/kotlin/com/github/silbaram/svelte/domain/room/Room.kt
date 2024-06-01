package com.github.silbaram.svelte.domain.room

import io.netty.channel.group.ChannelGroup
import java.util.UUID

data class Room(
    val roomId: UUID,
    val channelGroup: ChannelGroup
)