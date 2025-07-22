package io.github.octestx.basic.multiplatform.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.octestx.basic.multiplatform.ui.ui.global.GlobalVarProvider
import io.github.octestx.basic.multiplatform.ui.ui.theme.ThemeRepository
import io.github.octestx.basic.multiplatform.ui.ui.utils.ToastUI
import io.github.octestx.basic.multiplatform.ui.ui.utils.ToastUIState
import moe.tlaster.precompose.PreComposeApp

lateinit var toast: ToastUIState private set

@Composable
fun BasicMUIWrapper(content: @Composable () -> Unit) {
    GlobalVarProvider {
        ThemeRepository.currentTheme.second.UI() {
            PreComposeApp {
                toast = remember { ToastUIState() }
                content()
                ToastUI(toast)
            }
        }
    }
}