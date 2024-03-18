package com.github.silbaram.infrastructures.protocol.codec.json

import com.github.silbaram.infrastructures.protocol.Message
import com.github.silbaram.infrastructures.protocol.codec.Encoder
import com.github.silbaram.infrastructures.protocol.codec.json.JsonSerializerFactory.Companion.MAPPER
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.StringWriter

class JsonEncoder: Encoder {
    private val logger: Logger = LoggerFactory.getLogger(JsonEncoder::class.java)

    override fun messageEncode(message: Message): ByteArray {
        return try {
            val content: String = object2String(message)
            val body = content.toByteArray()
            body
        } catch (e: java.lang.Exception) {
            logger.error("An error occurred while reading the message.", e)
            ByteArray(0)
        }
    }

    private fun object2String(json: Any): String {
        val writer = StringWriter()
        try {
            MAPPER.writeValue(writer, json)
        } catch (e: Exception) {
            return ""
        }
        return writer.toString()
    }
}