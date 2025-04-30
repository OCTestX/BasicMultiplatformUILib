package io.github.octestx.basic.multiplatform.ui.ui.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun GlobalVarProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalLanguage provides LanguageRepository.currentLanguage.value
    ) {
        content()
    }
}