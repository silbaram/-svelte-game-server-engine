package com.github.silbaram.infrastructures.network.configuration

import com.github.silbaram.infrastructures.network.protocol.codec.SerializerFactory
import com.github.silbaram.infrastructures.network.protocol.codec.json.JsonSerializerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CodecAutoFactoryConfiguration {

    @Bean
    @ConditionalOnExpression("'\${svelte-app.network.protocol.codec}' == 'json'")
    fun createJsonSerializerFactory(): SerializerFactory = JsonSerializerFactory()
}