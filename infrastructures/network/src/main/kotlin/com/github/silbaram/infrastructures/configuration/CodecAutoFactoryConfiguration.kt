package com.github.silbaram.infrastructures.configuration

import com.github.silbaram.infrastructures.protocol.codec.SerializerFactory
import com.github.silbaram.infrastructures.protocol.codec.json.JsonSerializerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CodecAutoFactoryConfiguration {

    @Bean
    @ConditionalOnExpression("'\${app.network.protocol.codec}' == 'json'")
    fun createJsonSerializerFactory(): SerializerFactory = JsonSerializerFactory()
}