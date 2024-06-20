package com.lk.retrofit.compiler.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.lk.retrofit.compiler.annotations.BasicRetrofitApi
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.WildcardTypeName
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
                    TypeSpec.classBuilder("${className}Repository")
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
                                val typeVariable = TypeVariableName(
                                    "T",
                                    ClassName(pkg, "BasicRetrofitResultData")
                                        .parameterizedBy(WildcardTypeName.producerOf(ANY))
                                ).copy(reified = true)
                                kspLogger.warn("ReturnType: ${returnType}")
                                if (funName != "hashCode" && funName != "toString" && funName != "equals") {
                                    ts.addFunction(
                                        FunSpec.builder(func.simpleName.asString())
                                            .addModifiers(KModifier.SUSPEND, KModifier.INLINE)
                                            .addKdoc(docS)
                                            .addTypeVariable(typeVariable)
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
                                                        typeVariable
                                                            .copy(nullable = false)
                                                    )
                                                }
                                                val paramSb =
                                                    StringBuilder("val result = ${paramApiName}.${funName}(")

                                                for (param in parameters) {
                                                    paramSb.append("${param.name?.asString()}, ")
                                                }
                                                paramSb.append(").response")
                                                val result = "\"\${result}\""
                                                val eMsg = "\${e.message}"
                                                fs.addCode(
                                                    """
                                                        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                                            try {
                                                                ${paramSb}
                                                                timber.log.Timber.d($result)
                                                                resultSucOperation(this, result as T)
                                                                result as T
                                                            } catch (e: Exception) {
                                                                timber.log.Timber.e(e)
                                                                T::class.java.getDeclaredConstructor().newInstance().also {
                                                                      it.code = -1
                                                                      it.msg = "$eMsg"
                                                                 }
                                                            }
                                                        }
                                                    """.trimIndent()
                                                )
                                            }
                                            // 返回成功的回调函数
                                            .addParameter(
                                                ParameterSpec
                                                    .builder("resultSucOperation", LambdaTypeName
                                                        .get(returnType = UNIT, parameters = listOf(
                                                            ParameterSpec.builder("coroutineScope", ClassName("kotlinx.coroutines", "CoroutineScope")).build(),
                                                            ParameterSpec.builder("result", typeVariable).build()
                                                        )))
                                                    .defaultValue("{ _, _ ->}")
                                                    .addModifiers(KModifier.CROSSINLINE)
                                                    .build()
                                            )
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