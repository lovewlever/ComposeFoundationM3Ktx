package com.gq.basicm3

import android.app.Application
import com.gq.basicm3.common.TimberCloseTree
import com.gq.basicm3.extension.isApkInDebug
import com.gq.basicm3.widget.AppCrashHandler
import com.gq.basicm3.widget.TimberFileTree
import com.jakewharton.processphoenix.ProcessPhoenix
import timber.log.Timber

object AppContext {

    lateinit var application: Application

    var initProcessPhoenix = false
    var initTimberDebugTree = true
    var initTimberFileTree = false
    var initExceptionHandler = true

    fun initialization(application: Application, init: AppContext.() -> Unit = {}) {
        this.application = application
        this.init()
        if (initProcessPhoenix) {
            if (ProcessPhoenix.isPhoenixProcess(application)) {
                return
            }
        }

        if (initTimberDebugTree) {
            // 日志
            if (application.applicationInfo.isApkInDebug()) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(TimberCloseTree())
            }
        }

        if (initTimberFileTree) {
            Timber.plant(TimberFileTree())
        }

        if (initExceptionHandler) {
            setDefaultUncaughtExceptionHandler()
        }
    }

    private fun setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppCrashHandler())
    }
}