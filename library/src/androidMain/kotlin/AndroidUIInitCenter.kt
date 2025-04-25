package io.github.octestx.basic.multiplatform.ui

import crash.CrashHandler

object AndroidUIInitCenter {
    private var initialized = false
    fun init() {
        CommonUIInitCenter.init {
            CrashHandler.init()
        }
    }
}