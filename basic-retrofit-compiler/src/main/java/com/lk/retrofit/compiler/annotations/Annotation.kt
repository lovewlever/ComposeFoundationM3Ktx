package com.lk.retrofit.compiler.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class BasicRetrofitApi

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class BasicInitRetrofit(
    val hostName: String,
    val interceptors: Array<KClass<*>>,
    val callAdapterFactory: Array<KClass<*>>,
    val converterFactory: Array<KClass<*>>
)
