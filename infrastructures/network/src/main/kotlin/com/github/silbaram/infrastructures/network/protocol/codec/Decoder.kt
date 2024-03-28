package com.github.silbaram.infrastructures.network.protocol.codec

import com.github.silbaram.infrastructures.network.protocol.Message

interface Decoder {

    fun messageDecode(msgClazz: Class<*>, body: ByteArray): Message
}