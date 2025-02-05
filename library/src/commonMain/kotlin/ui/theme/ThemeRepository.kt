package io.github.kotlin.fibonacci.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import com.russhwolf.settings.Settings
import io.klogging.noCoLogger

object ThemeRepository {
    private val ologger = noCoLogger<ThemeRepository>()
    private val _allTheme = mutableStateMapOf<String, M3Theme>(
        "default" to M3Theme.default()
    )
    val allTheme: Map<String, M3Theme> = _allTheme
    private var _currentKey by mutableStateOf("default")
    val currentTheme by derivedStateOf {
        run {
            val key = _currentKey
            val theme = allTheme[key]
            if (theme == null) "default" to allTheme["default"]!!
            else key to theme
        }
    }

    /**
     * 在这里注册你的主题
     * 如果要覆盖默认主题请把key设置为default
     */
    fun bindTheme(key: String, theme: M3Theme) {
        _allTheme[key] = theme
    }
    fun getTheme(key: String): M3Theme = allTheme[key]?: run {
        ologger.warn { "未找到Theme：$key,使用默认主题" }
        allTheme["default"]!!
    }
    fun existsTheme(key: String): Boolean = allTheme.containsKey(key)
    fun switchTheme(key: String): Result<Unit> {
        if (allTheme[key] == null) return Result.failure(ThemeNoFoundException())
        _currentKey = key
        return Result.success(Unit)
    }

    internal fun initLoad() {
        _currentKey = Settings().getString("${this.javaClass.name}-currentThemeKey", "default")
    }

    abstract class M3Theme() {
        @Composable
        abstract fun colorScheme(): ColorScheme
        @Composable
        abstract fun shapes(): Shapes
        @Composable
        abstract fun typography(): Typography
        @Composable
        fun UI(content: @Composable () -> Unit) {
            MaterialTheme(
                colorScheme = colorScheme(),
                shapes = shapes(),
                typography = typography()
            ) {
                content()
            }
        }
        companion object{
            fun default() = object : M3Theme() {
                @Composable
                override fun colorScheme(): ColorScheme = MaterialTheme.colorScheme
                @Composable
                override fun shapes(): Shapes = MaterialTheme.shapes
                @Composable
                override fun typography(): Typography = MaterialTheme.typography
            }
            @Composable
            fun create(
                colorScheme: ColorScheme = MaterialTheme.colorScheme,
                shapes: Shapes = MaterialTheme.shapes,
                typography: Typography = MaterialTheme.typography
            ) = object :M3Theme() {
                @Composable
                override fun colorScheme(): ColorScheme = colorScheme
                @Composable
                override fun shapes(): Shapes = shapes
                @Composable
                override fun typography(): Typography = typography
            }
        }
    }
    class ThemeNoFoundException(): Exception()
}