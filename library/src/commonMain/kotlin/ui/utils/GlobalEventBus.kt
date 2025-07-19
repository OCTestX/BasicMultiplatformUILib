package io.github.octestx.basic.multiplatform.ui.ui.utils

import kotlinx.coroutines.flow.*

// 定义一个事件总线单例对象
object GlobalEventBus {
    // 使用 extraBufferCapacity 防止在没有活跃订阅者时阻塞发布者
    private val _events = MutableSharedFlow<Any>(extraBufferCapacity = 64)

    // 对外只暴露只读 Flow
    val events = _events.asSharedFlow()

    /**
     * 发布一个事件到事件总线
     *
     * @param event 事件对象，可以是任意类型
     */
    suspend fun post(event: Any) {
        _events.emit(event)
    }

    /**
     * 订阅特定类型的事件
     *
     * 内部利用 Flow 的 filter/map 操作，将全局事件流中符合类型 T 的事件筛选出来并转换成 Flow<T> 返回，
     * 这样订阅者就能只关心自己所需的事件类型而无需进行额外判断。
     *
     * @return 一个只包含类型 [T] 事件的 Flow
     */
    inline fun <reified T> subscribe(): Flow<T> {
        return events.filter { it is T }.map { it as T }
    }
}
