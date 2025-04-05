package io.github.kotlin.fibonacci

import androidx.compose.ui.window.TrayState
import io.github.kotlin.fibonacci.ui.utils.SystemMessage

object JVMUIInitCenter {
    private var initialized = false
    fun init(trayState: TrayState) {
        if (initialized) return
        SystemMessage.init(trayState)
        CommonUIInitCenter.init {

        }
    }
}