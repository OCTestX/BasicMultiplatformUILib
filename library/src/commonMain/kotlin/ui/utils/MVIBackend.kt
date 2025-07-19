package io.github.octestx.basic.multiplatform.ui.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class MVIBackend<S : MVIBackend.IntentState, E : MVIBackend.IntentEvent> {
    // need data class, and all val
    abstract class IntentState {}

    // need sealed class, use data class or data object,all val
    abstract class IntentEvent {}

    protected abstract suspend fun processIntent(event: E)

    @Composable
    protected abstract fun CalculateState(): S

    fun emitIntent(event: E) {
        scope.launch {
            processIntent(event)
        }
    }

    lateinit var scope: CoroutineScope private set

    @Composable
    fun CurrentState(): S {
        scope = rememberCoroutineScope()
        return CalculateState()
    }
}