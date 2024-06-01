package com.github.silbaram.svelte.domain.user

import io.netty.channel.Channel
import io.netty.channel.ChannelId

data class User(
    val channelId: ChannelId,
    val channel: Channel,
    val username: String
)
