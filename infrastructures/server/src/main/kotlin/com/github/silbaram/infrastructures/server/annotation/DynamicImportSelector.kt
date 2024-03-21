package com.github.silbaram.infrastructures.server.annotation

import com.github.silbaram.infrastructures.server.configuration.NettyServerConfigurationTemplate
import org.springframework.context.annotation.ImportSelector
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata


class DynamicImportSelector : ImportSelector {
    override fun selectImports(annotationMetadata: AnnotationMetadata): Array<String> {
        // 동적으로 설정 클래스를 결정하는 로직
        val attributesMap: MutableMap<String, Any>? = annotationMetadata.getAnnotationAttributes(EnableNettyServer::class.java.getName(), false)
        val attributes = AnnotationAttributes.fromMap(attributesMap)

        val typeValue = attributes?.getClass<NettyServerConfigurationTemplate>("nettyServerConfiguration")

        return typeValue?.kotlin?.objectInstance?.let { arrayOf(it::class.java.name) } ?: arrayOf()
    }
}