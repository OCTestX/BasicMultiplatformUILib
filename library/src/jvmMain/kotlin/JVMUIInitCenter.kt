import androidx.compose.ui.window.TrayState
import io.github.octestx.basic.multiplatform.ui.CommonUIInitCenter

object JVMUIInitCenter {
    private var initialized = false
    fun init(trayState: TrayState) {
        if (initialized) return
        SystemMessage.init(trayState)
        CommonUIInitCenter.init {

        }
    }
}