package com.github.silbaram.infrastructures.server.annotation

import com.github.silbaram.infrastructures.server.configuration.NettyServerTemplate
import org.springframework.context.annotation.Import
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(DynamicServerImportSelector::class)
annotation class EnableNettyServer(
    val serverConfigurationClasses: Array<KClass<out NettyServerTemplate>>
)