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

    fun initialization(application: Application, ) {
        this.application = application
        if (ProcessPhoenix.isPhoenixProcess(application)) {
            return
        }
        // 日志
        if (application.applicationInfo.isApkInDebug()) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberCloseTree())
        }
        Timber.plant(TimberFileTree())
        setDefaultUncaughtExceptionHandler()
    }

    private fun setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppCrashHandler())
    }

    fun isApkInDebug() =
        application.applicationInfo.isApkInDebug()
}