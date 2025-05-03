package io.github.octestx.basic.multiplatform.ui.ui.global

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// 定义语言枚举
enum class Language(val text: String) {
    English("\uD83C\uDF10 International English"), ChineseSimplified("\uD83C\uDDE8\uD83C\uDDF3 简体中文"), ChineseTraditional(
        "\uD83C\uDC04 繁體中文"
    ),
    Japanese("\uD83C\uDDEF\uD83C\uDDF5 日本語"),
}

// 创建 CompositionLocal，默认值为英语
val LocalLanguage = compositionLocalOf { Language.English }

object LanguageRepository {
    private var _currentLanguage = MutableStateFlow(Language.English)
    val currentLanguage: StateFlow<Language> = _currentLanguage
    fun switchLanguage(language: Language) {
        _currentLanguage.value = language
    }
}