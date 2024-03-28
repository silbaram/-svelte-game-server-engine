package com.github.silbaram.infrastructures.network.protocol.codec

import com.github.silbaram.infrastructures.network.protocol.Message

interface Encoder {

    fun messageEncode(message: Message): ByteArray
}