package com.lk.retrofit.compiler.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.lk.retrofit.compiler.annotations.BasicInitRetrofit
import com.lk.retrofit.compiler.common.Constants
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class RetrofitInitGen {

    fun gen(kspLogger: KSPLogger, kspCodeGenerator: CodeGenerator, resolver: Resolver) {
        val annotateds: Sequence<KSAnnotated> = resolver.getSymbolsWithAnnotation(BasicInitRetrofit::class.java.name)
        annotateds.forEach { anno ->
            kspLogger.warn("$anno")
        }
        val functionDeclarations = annotateds.filterIsInstance<KSFunctionDeclaration>()
        functionDeclarations.forEachIndexed { index, func ->
            val parentKSClass = (func.parentDeclaration as? KSClassDeclaration)
            val className = parentKSClass?.simpleName?.asString()
            val pkg = Constants.RetrofitInitGenPackageNamePrefix//func.packageName.asString()
            kspLogger.warn("${className}, ${pkg}", parentKSClass)

            val annotation = func.annotations.first { it.shortName.asString() == BasicInitRetrofit::class.java.simpleName }
            val hostName = annotation.arguments.first { it.name?.asString() == "hostName" }.value
            kspLogger.warn("hostName====>${hostName}}")
            val cache = annotation.arguments.first { it.name?.asString() == "cache" }.value
            kspLogger.warn("cache====>${cache}}")
            val interceptors = annotation.arguments.first { it.name?.asString() == "interceptors" }.value as ArrayList<KSType>
            kspLogger.warn("interceptors====>${interceptors}}")
            val callAdapterFactory = annotation.arguments.first { it.name?.asString() == "callAdapterFactory" }.value as ArrayList<KSType>
            kspLogger.warn("interceptors====>${callAdapterFactory}}")
            val converterFactory = annotation.arguments.first { it.name?.asString() == "converterFactory" }.value as ArrayList<KSType>
            kspLogger.warn("interceptors====>${converterFactory}}")

            FileSpec.builder("${pkg}.retrofit.init", "${className}RetrofitInit_Gen")
                .addType(
                    TypeSpec.classBuilder("${className}RetrofitInit")
                        .addFunction(
                            FunSpec.builder("initRetrofit")
                                .addParameter(
                                    ParameterSpec
                                        .builder( "basicRetrofit",ClassName("com.gq.basicm3.retrofit", "BasicRetrofit"))
                                        .build()
                                )
                                .also { fs ->
                                    val sb = StringBuilder()
                                    interceptors.forEach {
                                        sb.append("addInterceptor(")
                                            .append("${it.toClassName().packageName}.${it.toClassName().simpleName}()")
                                            .append(")")
                                    }
                                    val sb2 = StringBuilder()
                                    callAdapterFactory.forEach {
                                        sb2.append("addCallAdapterFactory(")
                                            .append("${it.toClassName().packageName}.${it.toClassName().simpleName}()")
                                            .append(")")
                                    }
                                    val sb3 = StringBuilder()
                                    converterFactory.forEach {
                                        sb3.append("addConverterFactory(")
                                        val clazz = "${it.toClassName().packageName}.${it.toClassName().simpleName}"
                                        if (clazz == "retrofit2.converter.gson.GsonConverterFactory") {
                                            sb3.append("${it.toClassName().packageName}.${it.toClassName().simpleName}.create()")
                                        } else {
                                            sb3.append("${it.toClassName().packageName}.${it.toClassName().simpleName}()")
                                        }
                                        sb3.append(")")
                                    }
                                    fs.addCode(
                                    """
                                        basicRetrofit.initialization(
                                            baseUrl = "${hostName}", 
                                            cache = ${cache},
                                            okHttpClientBuilder = {
                                            ${sb}
                                        }, retrofitBuilder = {
                                            ${sb2}
                                            ${sb3}
                                        })
                                    """.trimIndent()
                                )
                                }
                                .build()
                        )
                        .build()
                ).build()
                .writeTo(kspCodeGenerator, Dependencies(true))
        }
    }
}