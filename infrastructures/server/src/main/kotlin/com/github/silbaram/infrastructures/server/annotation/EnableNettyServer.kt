package com.github.silbaram.infrastructures.server.annotation

import com.github.silbaram.infrastructures.server.configuration.NettyServerConfigurationTemplate
import org.springframework.context.annotation.Import
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(DynamicImportSelector::class)
annotation class EnableNettyServer(
    val nettyServerConfiguration: KClass<out NettyServerConfigurationTemplate>
)
