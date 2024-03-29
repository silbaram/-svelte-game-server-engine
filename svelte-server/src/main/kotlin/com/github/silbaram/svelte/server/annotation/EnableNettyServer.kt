package com.github.silbaram.svelte.server.annotation

import com.github.silbaram.infrastructures.NetworkScan
import com.github.silbaram.infrastructures.server.configuration.NettyServerTemplate
import com.github.silbaram.svelte.server.SvelteServerScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(DynamicServerImportSelector::class)
@ComponentScan(basePackageClasses = [SvelteServerScan::class, NetworkScan::class])
annotation class EnableNettyServer(
    val serverConfigurationClasses: Array<KClass<out NettyServerTemplate>>
)