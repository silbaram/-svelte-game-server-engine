package com.github.silbaram.svelte.domain.user

import io.netty.channel.Channel
import io.netty.channel.ChannelId

data class User(
    val userId: String,
    val username: String,
    val channelId: ChannelId,
    val channel: Channel,
)
