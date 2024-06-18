package com.gq.basicm3.basis

import android.app.Application
import com.gq.basicm3.AppContext

open class BasicApplication: Application() {

    protected open val initProcessPhoenix = false
    protected open val initTimberDebugTree = true
    protected open val initTimberFileTree = false
    protected open val initExceptionHandler = true

    override fun onCreate() {
        super.onCreate()
        AppContext.initialization(this, init = {
            this.initProcessPhoenix = this@BasicApplication.initProcessPhoenix
            this.initTimberDebugTree = this@BasicApplication.initTimberDebugTree
            this.initTimberFileTree = this@BasicApplication.initTimberFileTree
            this.initExceptionHandler = this@BasicApplication.initExceptionHandler
        })
    }
}