package com.gq.basic.gradle.plugin.visitor

import com.gq.basic.gradle.plugin.common.Constants
import groovyjarjarasm.asm.Opcodes
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter


class RetrofitInitClassVisitor(private val nextClassVisitor: ClassVisitor): ClassVisitor(Opcodes.ASM9, nextClassVisitor) {

    private var className: String? = null

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<String>?) {
        className = name
        /*val fieldVisitor = cv.visitField(ACC_PRIVATE, "autoApplicationRetrofitInit", "Lcom/lk/auto/letter/retrofit/init/AutoApplicationRetrofitInit;", null, null);

        val annotationVisitor0 = fieldVisitor.visitAnnotation("Lorg/jetbrains/annotations/Nullable;", false)
        annotationVisitor0.visitEnd()
        fieldVisitor.visitEnd()*/

        /*fieldVisitor.visitEnd()
        val fieldVisitor = cv.visitField(ACC_PUBLIC, "autoApplicationRetrofitInit", "Lcom/lk/auto/letter/retrofit/init/AutoApplicationRetrofitInit;", null, null)
        val annotationVisitor0 = fieldVisitor.visitAnnotation("Ljavax/inject/Inject;", true)
        annotationVisitor0.visitEnd()
        fieldVisitor.visitEnd()*/
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        println("""
            -------
            access: Int = ${access}
            name: String = ${name}
            descriptor: String = ${descriptor}
            signature: String = ${exceptions}
            -------
        """.trimIndent())

        if (name == "onCreate" && className != null) {
            return object :AdviceAdapter(Opcodes.ASM9, methodVisitor, access, name, descriptor) {

                private var inSuper = false

                override fun onMethodEnter() {
                    super.onMethodEnter()
                    println("""
                        ---------onMethodEnter START--------
                        ---------onMethodEnter END--------
                    """.trimIndent())
                }

                override fun onMethodExit(opcode: Int) {
                    super.onMethodExit(opcode)
                    println("""
                        ---------onMethodExit START--------
                        opcode: Int = ${opcode}
                        ---------onMethodExit END--------
                    """.trimIndent())
                    if (inSuper) {
                        methodVisitor.visitVarInsn(ALOAD, 0)
                        methodVisitor.visitTypeInsn(NEW, "${Constants.RetrofitInitGenPackageNamePrefix}/retrofit/init/AutoApplicationRetrofitInit")
                        methodVisitor.visitInsn(DUP)
                        methodVisitor.visitMethodInsn(INVOKESPECIAL, "${Constants.RetrofitInitGenPackageNamePrefix}/retrofit/init/AutoApplicationRetrofitInit", "<init>", "()V", false)
                        methodVisitor.visitVarInsn(ALOAD, 0)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "$className", "getBasicRetrofit", "()Lcom/gq/basicm3/retrofit/BasicRetrofit;", false)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "${Constants.RetrofitInitGenPackageNamePrefix}/retrofit/init/AutoApplicationRetrofitInit", "initRetrofit", "(Lcom/gq/basicm3/retrofit/BasicRetrofit;)V", false)
                    }
                }

                override fun visitMethodInsn(
                    opcodeAndSource: Int,
                    owner: String?,
                    name: String?,
                    descriptor: String?,
                    isInterface: Boolean
                ) {
                    super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
                    if (opcodeAndSource == INVOKESPECIAL) {
                        inSuper = true
                    }
                    println("""
                        ---------visitMethodInsn START--------
                        opcodeAndSource: Int = ${opcodeAndSource}
                        owner: String? = ${owner}
                        name: String? = ${name}
                        descriptor: String? = ${descriptor}
                        isInterface: Boolean = ${isInterface}
                        ---------visitMethodInsn END--------
                    """.trimIndent())
                }

                override fun visitAnnotation(
                    descriptor: String?,
                    visible: Boolean
                ): AnnotationVisitor {
                    println("""
                        ---------visitAnnotation START--------
                        descriptor: String = ${descriptor}
                        visible: Boolean = ${visible}
                        ---------visitAnnotation END--------
                    """.trimIndent())
                    if (descriptor == "Lcom/lk/retrofit/compiler/annotations/BasicInitRetrofit;") {
                        println("""
                        ---------visitAnnotation True--------
                        "Lcom/lk/retrofit/compiler/annotations/BasicInitRetrofit;"
                        ---------visitAnnotation True--------
                    """.trimIndent())
                    }
                    return super.visitAnnotation(descriptor, visible)
                }
            }
        }

        return methodVisitor
    }
}