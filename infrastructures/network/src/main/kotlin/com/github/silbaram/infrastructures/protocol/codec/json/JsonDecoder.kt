package com.github.silbaram.infrastructures.protocol.codec.json

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.type.TypeFactory
import com.github.silbaram.infrastructures.protocol.Message
import com.github.silbaram.infrastructures.protocol.codec.Decoder
import com.github.silbaram.infrastructures.protocol.codec.json.JsonSerializerFactory.Companion.MAPPER
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JsonDecoder: Decoder {
    private val logger: Logger = LoggerFactory.getLogger(JsonDecoder::class.java)

    override fun messageDecode(msgClazz: Class<*>, body: ByteArray): Message {
        val content = String(body, charset("UTF-8"))
        return  string2Object(content, msgClazz) as Message
    }

    private fun <T> string2Object(json: String, clazz: Class<T>): T {
        val type: JavaType = TypeFactory.defaultInstance().constructType(clazz)
        return try {
            MAPPER.readValue(json, type) as T
        } catch (e: Exception) {
            logger.error("", e)
            "" as T
        }
    }
}