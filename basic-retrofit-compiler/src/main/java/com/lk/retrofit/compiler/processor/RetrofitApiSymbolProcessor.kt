package com.lk.retrofit.compiler.processor

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.lk.retrofit.compiler.annotations.RetrofitApi
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.concurrent.thread

/**
 * 生成请求类
 */
class RetrofitApiSymbolProcessor(
    private val kspLogger: KSPLogger,
    private val kspCodeGenerator: CodeGenerator
): SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        kspLogger.warn("RetrofitApiSymbolProcessor========================> START")
        val annotateds: Sequence<KSAnnotated> = resolver.getSymbolsWithAnnotation(RetrofitApi::class.java.name)
        val classDeclarations: Sequence<KSClassDeclaration> = annotateds.filterIsInstance<KSClassDeclaration>()
        classDeclarations.forEach { ksClassDeclaration ->
            thread(true) {
                val pkg = ksClassDeclaration.packageName.asString()
                val className = ksClassDeclaration.simpleName.asString()
                kspLogger.warn("$pkg; $className", ksClassDeclaration)
                val spec = FileSpec.builder(pkg, "${className}Repository")
                    .addType(
                        TypeSpec.classBuilder("${className}Repository")
                            .addModifiers(KModifier.OPEN)
                            .also {  ts ->

                            }
                            .build()
                    ).build()
                spec.writeTo(kspCodeGenerator, Dependencies(true))
            }
        }
        kspLogger.warn("RetrofitApiSymbolProcessor========================> END")
        return emptyList()
    }
}