package com.lk.retrofit.compiler.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.lk.retrofit.compiler.annotations.BasicRetrofitApi
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class ApiHiltModuleGen {

    fun gen(kspLogger: KSPLogger, kspCodeGenerator: CodeGenerator, resolver: Resolver) {
        val annotateds: Sequence<KSAnnotated> =
            resolver.getSymbolsWithAnnotation(BasicRetrofitApi::class.java.name)

        val classDeclarations: Sequence<KSClassDeclaration> =
            annotateds.filterIsInstance<KSClassDeclaration>()
        val list = classDeclarations.toList()
        if (list.isNotEmpty()) {
            val pkg = list[0].packageName.asString()
            FileSpec
                .builder(pkg, "ApiHiltModule_BasicGen")
                .addType(
                    // 类名
                    TypeSpec
                        .objectBuilder("ApiHiltModule_BasicGen")
                        .addAnnotation(
                            AnnotationSpec
                                .builder(ClassName("dagger.hilt", "InstallIn"))
                                .addMember(CodeBlock.builder().addStatement("dagger.hilt.components.SingletonComponent::class").build())
                                .build()
                        )
                        .addAnnotation(ClassName("dagger", "Module"))
                        .also { typeSpec ->
                            classDeclarations.forEach { ksClassDeclaration ->
                                typeSpec.addFunction(
                                    FunSpec.builder("provider${ksClassDeclaration.simpleName.asString()}")
                                        .addAnnotation(ClassName("dagger", "Provides"))
                                        .addAnnotation(ClassName("javax.inject", "Singleton"))
                                        .addParameter("basicRetrofit", ClassName("com.gq.basicm3.retrofit", "BasicRetrofit"))
                                        .returns(ksClassDeclaration.toClassName())
                                        .addCode("""
                                            return basicRetrofit.retrofit(${ksClassDeclaration.simpleName.asString()}::class)
                                        """.trimIndent())
                                        .build()
                                )
                            }
                        }
                        .build()
                )
                .build()
                .writeTo(kspCodeGenerator, Dependencies(true))


        }
    }
}