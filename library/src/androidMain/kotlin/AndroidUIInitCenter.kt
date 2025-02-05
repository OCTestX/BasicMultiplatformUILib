package io.github.kotlin.fibonacci

import android.app.Application
import crash.CrashHandler

object AndroidUIInitCenter {
    private var initialized = false
    fun init() {
        CommonUIInitCenter.init {
            CrashHandler.init()
        }
    }
}