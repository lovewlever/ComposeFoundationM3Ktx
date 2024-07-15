package com.gq.basic.gradle.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.gq.basic.gradle.plugin.factory.RetrofitInitVisitorFactory
import org.gradle.api.Plugin
import org.gradle.api.Project

class RetrofitInitPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("Hello RetrofitInitPlugin")
        val extension = target.extensions.getByType(AndroidComponentsExtension::class.java)
        extension.onVariants { variant ->
            //可以通过variant来获取当前编译环境的一些信息，最重要的是可以 variant.name 来区分是debug模式还是release模式编译
            variant.instrumentation.transformClassesWith(RetrofitInitVisitorFactory::class.java, InstrumentationScope.ALL) {}
            //InstrumentationScope.ALL 配合 FramesComputationMode.COPY_FRAMES可以指定该字节码转换器在全局生效，包括第三方lib
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }
}