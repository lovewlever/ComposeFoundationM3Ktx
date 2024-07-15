package com.lk.retrofit.compiler.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.lk.retrofit.compiler.gen.ApiHiltModuleGen
import com.lk.retrofit.compiler.gen.BasicRetrofitResultDataGen
import com.lk.retrofit.compiler.gen.RepositoryGen
import com.lk.retrofit.compiler.gen.RetrofitInitGen

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
        BasicRetrofitResultDataGen().gen(kspLogger, kspCodeGenerator, resolver)
        RepositoryGen().gen(kspLogger, kspCodeGenerator, resolver)
        RetrofitInitGen().gen(kspLogger, kspCodeGenerator, resolver)
        kspLogger.warn("RetrofitApiSymbolProcessor========================> END")
        return emptyList()
    }
}