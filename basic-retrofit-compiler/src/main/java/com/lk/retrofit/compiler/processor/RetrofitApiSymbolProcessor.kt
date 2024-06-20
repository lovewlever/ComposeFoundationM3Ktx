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
import com.lk.retrofit.compiler.gen.ApiHiltModuleGen
import com.lk.retrofit.compiler.gen.RepositoryGen
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.concurrent.thread

/**
 * 生成请求类
 */
class RetrofitApiSymbolProcessor(
    private val kspLogger: KSPLogger,
    private val kspCodeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        kspLogger.warn("RetrofitApiSymbolProcessor========================> START")
        ApiHiltModuleGen().gen(kspLogger, kspCodeGenerator, resolver)
        RepositoryGen().gen(kspLogger, kspCodeGenerator, resolver)
        kspLogger.warn("RetrofitApiSymbolProcessor========================> END")
        return emptyList()
    }
}