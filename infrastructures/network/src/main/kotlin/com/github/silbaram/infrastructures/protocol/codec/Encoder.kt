package com.github.silbaram.infrastructures.protocol.codec

import com.github.silbaram.infrastructures.protocol.Message

interface Encoder {

    fun messageEncode(message: Message): ByteArray
}