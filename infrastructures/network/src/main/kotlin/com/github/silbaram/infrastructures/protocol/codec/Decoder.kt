package com.github.silbaram.infrastructures.protocol.codec

import com.github.silbaram.infrastructures.protocol.Message

interface Decoder {

    fun messageDecode(msgClazz: Class<*>, body: ByteArray): Message
}