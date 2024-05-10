package com.gq.basicm3.basis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.gq.basicm3.common.DensityCommon

open class BasicActivity: ComponentActivity() {

    /*@Inject
    lateinit var systemUiController: SystemUiController*/

    override fun onCreate(savedInstanceState: Bundle?) {
        DensityCommon.initDisplayMetrics(window)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //setImmersiveMode()
    }

    private fun setImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
    }

}