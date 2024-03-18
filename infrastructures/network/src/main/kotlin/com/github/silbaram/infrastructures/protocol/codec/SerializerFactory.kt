package com.github.silbaram.infrastructures.protocol.codec

interface SerializerFactory {
    fun getDecoder(): Decoder
    fun getEncoder(): Encoder
}