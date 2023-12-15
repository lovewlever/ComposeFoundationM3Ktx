package com.gq.basicm3.widget

import android.os.Build
import com.gq.basicm3.AppContext
import com.jakewharton.processphoenix.ProcessPhoenix
import timber.log.Timber
import kotlin.system.exitProcess

/**
 * 崩溃记录
 */
class AppCrashHandler: Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        Timber.e(e)
        val sb = StringBuilder()
            .append("-Android-SDK:${Build.VERSION.SDK_INT}\n")
            .append("-厂商:${Build.BRAND}\n")
            .append("-手机型号:${Build.MODEL}\n")
            .append("-CPU架构:${Build.CPU_ABI}\n")
        Timber.e(sb.toString())

        // 重启app
        ProcessPhoenix.triggerRebirth(AppContext.application)
    }
}