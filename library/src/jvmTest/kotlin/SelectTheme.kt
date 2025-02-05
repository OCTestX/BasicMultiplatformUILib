import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.kotlin.fibonacci.JVMInitCenter
import io.github.kotlin.fibonacci.JVMUIInitCenter
import io.github.kotlin.fibonacci.ui.BasicMUIWrapper
import io.github.kotlin.fibonacci.ui.theme.ThemeRepository
import io.klogging.noCoLogger
import org.junit.Test
import kotlin.test.assertEquals


class SelectThemeTest {

    @Test
    fun app() {
        JVMInitCenter.init()
        JVMUIInitCenter.init()
        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = ""
            ) {
                BasicMUIWrapper {
                    Column {
                        Text("Select: ${ThemeRepository.currentTheme.first}", style = MaterialTheme.typography.titleLarge)
                        val p1 = ThemeRepository.M3Theme.create(colorScheme = darkColorScheme())
                        val p2 = ThemeRepository.M3Theme.create(colorScheme = darkColorScheme(primary = Color.Green))
                        Button(onClick = {
                            ThemeRepository.bindTheme("PLUS", p1)
                            ThemeRepository.bindTheme("P2", p2)
                        }) {
                            Text("ADD")
                        }
                        LazyColumn {
                            items(ThemeRepository.allTheme.keys.toList()) { key ->
                                ThemePreviewItem(key, ThemeRepository.getTheme(key)) {
                                    ThemeRepository.switchTheme(key)
                                    noCoLogger<SelectThemeTest>().info("FD: $key")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    private fun ThemePreviewItem(key: String, theme: ThemeRepository.M3Theme, select: () -> Unit) {
        Card(onClick = select) {
            Text(key, style = theme.typography().bodyMedium)
            Text("background", color = theme.colorScheme().background)
            Text("primary", color = theme.colorScheme().primary)
            Text("secondary", color = theme.colorScheme().secondary)
            Text("tertiary", color = theme.colorScheme().tertiary)
            Text("scrim", color = theme.colorScheme().scrim)
        }
    }
}