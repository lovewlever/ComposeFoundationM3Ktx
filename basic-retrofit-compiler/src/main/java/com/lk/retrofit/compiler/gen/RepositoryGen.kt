package com.lk.retrofit.compiler.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.lk.retrofit.compiler.annotations.BasicRetrofitApi
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class RepositoryGen {

    fun gen(kspLogger: KSPLogger, kspCodeGenerator: CodeGenerator, resolver: Resolver) {
        val annotateds: Sequence<KSAnnotated> =
            resolver.getSymbolsWithAnnotation(BasicRetrofitApi::class.java.name)

        val classDeclarations: Sequence<KSClassDeclaration> =
            annotateds.filterIsInstance<KSClassDeclaration>()

        classDeclarations.forEach { ksClassDeclaration ->
            val pkg = ksClassDeclaration.packageName.asString()
            val className = ksClassDeclaration.simpleName.asString()
            val funcs = ksClassDeclaration.getAllFunctions()
            val paramApiName = className.lowercase()
            val paramClassName = ClassName(pkg, className)
            kspLogger.warn("$pkg; $className", ksClassDeclaration)
            val spec = FileSpec.builder(pkg, "${className}Repository_BasicGen")
                .addType(
                    // 类名
                    TypeSpec.classBuilder("${className}Repository_BasicGen")
                        .addModifiers(KModifier.OPEN)
                        .addAnnotation(
                            ClassName(
                                "dagger.hilt.android.scopes",
                                "ViewModelScoped"
                            )
                        )
                        // @Inject constructor()
                        .primaryConstructor(
                            FunSpec.constructorBuilder()
                                .addAnnotation(ClassName("javax.inject", "Inject"))
                                // 添加参数 Api
                                .addParameter(
                                    ParameterSpec
                                        .builder(paramApiName, paramClassName)
                                        .build()
                                ).build()
                        )
                        .addProperty(
                            PropertySpec.builder(paramApiName, paramClassName)
                                .mutable(false)
                                .addModifiers(KModifier.PRIVATE)
                                .initializer(paramApiName)
                                .build()
                        )
                        .also { ts ->
                            // 方法
                            funcs.forEach { func ->
                                val docS = func.docString ?: ""
                                val funName = func.simpleName.asString()
                                val parameters = func.parameters
                                val returnType = func.returnType?.resolve()?.arguments?.let {
                                    if (it.isNotEmpty()) it[0] else null
                                }
                                kspLogger.warn("ReturnType: ${returnType}")
                                if (funName != "hashCode" && funName != "toString" && funName != "equals") {
                                    ts.addFunction(
                                        FunSpec.builder(func.simpleName.asString())
                                            .addModifiers(KModifier.SUSPEND)
                                            .addKdoc(docS)
                                            .also { fs ->
                                                // 添加 Parameters
                                                for (parameter in parameters) {
                                                    fs.addParameter(
                                                        parameter.name?.asString()!!,
                                                        parameter.type.toTypeName()
                                                    )
                                                }
                                                // 添加返回值
                                                if (returnType != null) {
                                                    fs.returns(
                                                        returnType.toTypeName()
                                                            .copy(nullable = true)
                                                    )
                                                }
                                                val paramSb =
                                                    StringBuilder("val result = ${paramApiName}.${funName}(")

                                                for (param in parameters) {
                                                    paramSb.append("${param.name?.asString()}, ")
                                                }
                                                paramSb.append(").obtain")
                                                val result = "\"\${result}\""
                                                fs.addCode(
                                                    """
                                                        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                                            try {
                                                                ${paramSb}
                                                                timber.log.Timber.d($result)
                                                                result
                                                            } catch (e: Exception) {
                                                                timber.log.Timber.e(e)
                                                                null
                                                            }
                                                        }
                                                    """.trimIndent()
                                                )
                                            }
                                            .build()
                                    )
                                }
                            }
                        }
                        .build()
                ).build()
            spec.writeTo(kspCodeGenerator, Dependencies(true))
        }

    }
}