package io.github.kotlin.fibonacci

import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import io.github.kotlin.fibonacci.ui.utils.getAbsoluteFromResPath
import io.github.kotlin.fibonacci.utils.OS
import io.klogging.noCoLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

object SystemMessage {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val ologger = noCoLogger<SystemMessage>()
    private val notificationChannel = Channel<Pair<Notification, String?>>(0)
    private val notificationFlow: Flow<Pair<Notification, String?>>
        get() = notificationChannel.receiveAsFlow()
    fun sendNotification(notification: Notification, iconResPath: String? = null) {
        notificationChannel.trySend(notification to iconResPath)
    }
    internal fun init(trayState: TrayState) {
        notificationFlow
            .onEach {
                showSystemNotification(trayState, it.first, it.second)
            }
            .launchIn(scope)
    }
    /**
     * 显示系统通知（严格使用平台原生机制，禁用 AWT 回退）
     * @throws NotificationFailedException 当通知发送失败时抛出
     */
    @Throws(NotificationFailedException::class)
    private suspend fun showSystemNotification(
        trayState: TrayState,
        notification: Notification,
        iconResPath: String?
    ) {
        try {
            when(OS.getCurrentOS()) {
                OS.OperatingSystem.WIN -> tryWindowsToastNotification(trayState, notification.title, notification.message)
                OS.OperatingSystem.LINUX -> tryLinuxNativeNotification(notification.title, notification.message, iconResPath)
                else -> throw NotificationFailedException("Unsupported platform")
            }
        } catch (e: Throwable) {
            ologger.error(e) { "Failed to send notification: ${e.message}" }
        }
    }

    // region Windows 通知中心实现
    private fun tryWindowsToastNotification(
        trayState: TrayState,
        title: String,
        message: String,
    ) {
        trayState.sendNotification(
            Notification(
                title = title,
                message = message,
                type = Notification.Type.Warning
            )
        )
//        try {
//            // 构建 PowerShell 脚本
//            val psScript = buildString {
//                //加载必要程序集
//                appendLine("Add-Type -AssemblyName System.Runtime.WindowsRuntime")
//                appendLine("\$null = [Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType=WindowsRuntime]")
//                appendLine("\$null = [Windows.Data.Xml.Dom.XmlDocument, Windows.Data.Xml.Dom.XmlDocument, ContentType=WindowsRuntime]")
//
//                // 构建 XML 模板
//                appendLine("\$xmlTemplate = @\"")
//                appendLine("<toast>")
//                appendLine("<visual>")
//                appendLine("<binding template=\"ToastGeneric\">")
//                appendLine("<text>$title</text>")
//                appendLine("<text>$message</text>")
//                if (iconPath != null) {
//                    appendLine("<image placement=\"appLogoOverride\" src=\"file:///$iconPath\"/>")
//                }
//                appendLine("</binding>")
//                appendLine("</visual>")
//                appendLine("<audio silent=\"false\"/>")
//                appendLine("</toast>")
//                appendLine("\"@")
//
//                // 创建并显示通知
//                appendLine("\$xmlDoc = New-Object Windows.Data.Xml.Dom.XmlDocument")
//                appendLine("\$xmlDoc.LoadXml(\$xmlTemplate)")
//                appendLine("\$toast = [Windows.UI.Notifications.ToastNotification]::new(\$xmlDoc)")
//                appendLine("[Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier(\"Microsoft.Windows.Explorer\").Show(\$toast)")
//            }
//
//            ologger.info { "PowerShell script:\n$psScript"}
//
//            // 执行 PowerShell
//            val process = Runtime.getRuntime().exec(arrayOf(
//                "powershell.exe",
//                "-ExecutionPolicy", "Bypass",
//                "-Command", psScript
//            ))
//
//            val exitCode = process.waitFor()
//            if (exitCode != 0) {
//                throw IOException("PowerShell exit code: $exitCode")
//            }
//        } catch (e: Exception) {
//            throw NotificationFailedException("Windows notification failed: ${e.message}")
//        }
    }
    // endregion

    // region Linux 实现
    // region 原生通知实现
    private suspend fun tryLinuxNativeNotification(
        title: String,
        message: String,
        iconResPath: String?
    ): Boolean {
        return try {
            val cmd = mutableListOf("notify-send", "\""+title+"\"", "\""+message+"\"")

            // 处理图标资源（转换为临时文件）
            iconResPath?.let { path ->
                getAbsoluteFromResPath(path).let {
                    cmd += "-i"
                    cmd += it
                }
            }

            withContext(Dispatchers.IO) {
                Runtime.getRuntime()
                    .exec(cmd.toTypedArray())
                    .waitFor() == 0
            }
        } catch (e: Exception) {
            false
        }
    }
    // endregion

    class NotificationFailedException(message: String) : Exception(message)
    // endregion
}