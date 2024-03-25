package com.github.silbaram.infrastructures.server.annotation

import org.springframework.context.annotation.ImportSelector
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata


class DynamicServerImportSelector : ImportSelector {

    override fun selectImports(annotationMetadata: AnnotationMetadata): Array<String> {
        val attributesMap: MutableMap<String, Any>? = annotationMetadata.getAnnotationAttributes(EnableNettyServer::class.java.getName(), false)
        val attributes = AnnotationAttributes.fromMap(attributesMap)

        val typeValues = attributes?.getClassArray("serverConfigurationClasses")
        if (typeValues.isNullOrEmpty()) {
            throw IllegalArgumentException("serverConfigurationClasses must be set")
        }

        return typeValues.map { it.name }.toTypedArray()
    }
}