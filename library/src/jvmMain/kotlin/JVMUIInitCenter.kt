package io.github.kotlin.fibonacci

import androidx.compose.ui.window.TrayState

object JVMUIInitCenter {
    private var initialized = false
    fun init(trayState: TrayState) {
        if (initialized) return
        SystemMessage.init(trayState)
        CommonUIInitCenter.init {

        }
    }
}