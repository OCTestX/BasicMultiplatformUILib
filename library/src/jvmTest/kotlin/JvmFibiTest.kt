package io.github.kotlin.fibonacci

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.kotlin.fibonacci.utils.ojson
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmFibiTest {

    @Test
    fun `test 3rd element`() {
        CommonInitCenter.init()
        assertEquals(5, generateFibi().take(3).last())
    }
}