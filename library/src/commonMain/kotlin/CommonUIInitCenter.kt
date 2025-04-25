package io.github.octestx.basic.multiplatform.ui

import org.koin.core.context.GlobalContext
import org.koin.dsl.module

internal object CommonUIInitCenter {
    private var initialized = false
    // 延迟加载配置，优先使用注入的实例
    inline fun init(platformInit: () -> Unit) {
        if (initialized) return
        //TODO INIT
        val configuration = getKoinInjectedConfiguration()
        platformInit()
        initialized = true
    }
    private fun getKoinInjectedConfiguration(): UILibConfiguration {
        val current: UILibConfiguration = try {
            // 尝试获取 Koin 实例
            val koin = GlobalContext.get()
            // 从 Koin 获取配置，若不存在则返回默认
            koin.getOrNull<UILibConfiguration>() ?: DefaultUILibConfiguration()
        } catch (e: Exception) {
            // Koin 未启动或其他异常时返回默认
            DefaultUILibConfiguration()
        }
        return current
    }
}
// 库的 Koin 模块，声明默认配置
val uiLibKoinModule = module {
    // 使用单例，允许使用者覆盖
    single<UILibConfiguration> { DefaultUILibConfiguration() }
}
// 定义配置接口
interface UILibConfiguration {
}
// 默认配置实现
open class DefaultUILibConfiguration : UILibConfiguration {
}