package com.gq.basic.gradle.plugin.factory

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.gq.basic.gradle.plugin.visitor.RetrofitInitClassVisitor
import org.objectweb.asm.ClassVisitor

abstract class RetrofitInitVisitorFactory() : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return RetrofitInitClassVisitor(nextClassVisitor)
    }


    //通过classData中的当前类的信息，用来过滤哪些类需要执行字节码转换，这里支持通过类名，包名，注解，接口，父类等属性来组合判断
    override fun isInstrumentable(classData: ClassData): Boolean {
        println("RetrofitInitVisitorFactory - ClassName: ${classData.className}")
        val isSuperBasicM3 = classData.superClasses.contains("com.gq.basicm3.basis.BasicApplication")
        println("RetrofitInitVisitorFactory - SuperClassesIsBasicM3: ${isSuperBasicM3}")
        //指定包名执行
        return isSuperBasicM3

    }
}