package io.github.octestx.basic.multiplatform.ui

import io.github.octestx.basic.multiplatform.ui.crash.CrashHandler

object AndroidUIInitCenter {
    private var initialized = false
    fun init() {
        CommonUIInitCenter.init {
            CrashHandler.init()
        }
    }
}