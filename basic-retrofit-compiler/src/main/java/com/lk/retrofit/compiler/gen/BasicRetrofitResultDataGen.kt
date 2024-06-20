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
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class BasicRetrofitResultDataGen {

    fun gen(kspLogger: KSPLogger, kspCodeGenerator: CodeGenerator, resolver: Resolver) {
        val annotateds: Sequence<KSAnnotated> =
            resolver.getSymbolsWithAnnotation(BasicRetrofitApi::class.java.name)

        val classDeclarations: Sequence<KSClassDeclaration> =
            annotateds.filterIsInstance<KSClassDeclaration>()
        val list = classDeclarations.toList()
        if (list.isNotEmpty()) {
            val pkg = list[0].packageName.asString()
            val gsonClassName = ClassName("com.google.gson.annotations", "SerializedName")
            val typeVariable = TypeVariableName("T")
            FileSpec
                .builder(pkg, "BasicRetrofitResultData_BasicGen")
                .addType(
                    TypeSpec
                        .classBuilder("BasicRetrofitResultData")
                        .addModifiers(KModifier.OPEN)
                        .addTypeVariable(typeVariable)
                        .addSuperinterface(ClassName("java.io", "Serializable"))
                        // @SerializedName("code")
                        //    var code: Int = 0,
                        //    @SerializedName("data")
                        //    val `data`: T? = null,
                        //    @SerializedName("msg")
                        //    var msg: String = "",
                        //    @SerializedName("token")
                        //    val token: String = "",
                        .primaryConstructor(
                            FunSpec.constructorBuilder()
                                .addParameter(
                                    ParameterSpec
                                        .builder("code", Int::class.asTypeName().copy(nullable = true))
                                        .addAnnotation(AnnotationSpec.builder(gsonClassName).addMember("\"code\"").build())
                                        .defaultValue("null")
                                        .build()
                                )
                                .addParameter(
                                    ParameterSpec
                                        .builder("data", typeVariable.copy(nullable = true))
                                        .addAnnotation(AnnotationSpec.builder(gsonClassName).addMember("\"data\"").build())
                                        .defaultValue("null")
                                        .build()
                                )
                                .addParameter(
                                    ParameterSpec
                                        .builder("msg", String::class.asTypeName().copy(nullable = true))
                                        .addAnnotation(AnnotationSpec.builder(gsonClassName).addMember("\"msg\"").build())
                                        .defaultValue("null")
                                        .build()
                                )
                                .build()
                        )
                        .addProperty(PropertySpec.builder("code", Int::class.asTypeName().copy(nullable = true))
                            .mutable(mutable = true)
                            .initializer("code")
                            .build())
                        .addProperty(PropertySpec.builder("data", typeVariable.copy(nullable = true))
                            .mutable(false)
                            .initializer("data")
                            .build())
                        .addProperty(PropertySpec.builder("msg", String::class.asTypeName().copy(nullable = true))
                            .mutable(mutable = true)
                            .initializer("msg")
                            .build())
                        .build()
                )
                .build()
                .writeTo(kspCodeGenerator, Dependencies((true)))
        }
    }
}