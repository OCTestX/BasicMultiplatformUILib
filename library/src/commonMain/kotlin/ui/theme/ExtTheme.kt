package io.github.kotlin.fibonacci.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.warning @Composable get() = if(isSystemInDarkTheme()) Color(0xFFFFA900) else Color(0xFFFFD740)
val ColorScheme.info @Composable get() = if(isSystemInDarkTheme()) Color(0xFFB0BEC5) else Color(0xFF727272)
val ColorScheme.success @Composable get() = if(isSystemInDarkTheme()) Color(0xFF6EE7B7) else Color(0xFF28A745)