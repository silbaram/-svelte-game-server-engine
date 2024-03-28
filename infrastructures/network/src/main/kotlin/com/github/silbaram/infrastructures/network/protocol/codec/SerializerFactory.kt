package com.github.silbaram.infrastructures.network.protocol.codec

interface SerializerFactory {
    fun getDecoder(): Decoder
    fun getEncoder(): Encoder
}