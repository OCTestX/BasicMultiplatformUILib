package io.github.octestx.basic.multiplatform.ui.ui.global

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// 定义语言枚举
enum class Language {
    English, ChineseSimplified, ChineseTraditional, Japanese,
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