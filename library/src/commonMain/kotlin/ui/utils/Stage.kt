package io.github.octestx.basic.multiplatform.ui.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MutableStage<S>(private val default: S) : Stage<S> {
    // 私有可变状态流
    private val _currentState = MutableStateFlow<S>(default)

    // 对外暴露不可变状态流
    override val currentState: StateFlow<S> = _currentState.asStateFlow()

    @Composable
    override fun CurrentState(): S {
        return currentState.collectAsState().value // 获取当前状态
    }

    // 线程安全的同步更新方法（支持协程上下文）
    suspend fun setStage(newStage: S) {
        _currentState.value = newStage // 立即同步更新
    }
}

interface Stage<S> {
    // 对外暴露不可变状态流
    abstract val currentState: StateFlow<S>

    @Composable
    abstract fun CurrentState(): S
}