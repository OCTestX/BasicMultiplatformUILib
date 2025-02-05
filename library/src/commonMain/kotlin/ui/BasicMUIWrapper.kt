package io.github.kotlin.fibonacci.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.kotlin.fibonacci.ui.theme.ThemeRepository
import io.github.kotlin.fibonacci.ui.utils.ToastUI
import io.github.kotlin.fibonacci.ui.utils.ToastUIState

lateinit var toast: ToastUIState private set

@Composable
fun BasicMUIWrapper(content: @Composable () -> Unit) {
    ThemeRepository.currentTheme.second.UI() {
        toast = remember { ToastUIState() }
        content()
        ToastUI(toast)
    }
}