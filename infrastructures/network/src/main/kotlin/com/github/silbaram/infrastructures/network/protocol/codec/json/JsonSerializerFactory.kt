package com.github.silbaram.infrastructures.network.protocol.codec.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.silbaram.infrastructures.network.protocol.codec.Decoder
import com.github.silbaram.infrastructures.network.protocol.codec.Encoder
import com.github.silbaram.infrastructures.network.protocol.codec.SerializerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class JsonSerializerFactory: SerializerFactory {

    private val decoder: JsonDecoder = JsonDecoder()
    private val encoder: JsonEncoder = JsonEncoder()

    companion object {
        val MAPPER = ObjectMapper()
    }

    override fun getDecoder(): Decoder = decoder

    override fun getEncoder(): Encoder = encoder
}