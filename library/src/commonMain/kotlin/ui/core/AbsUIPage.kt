package ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AbsUIPage<P: Any?, S: AbsUIPage.AbsUIState<A>, A: AbsUIPage.AbsUIAction>(private val model: AbsUIModel<P, S, A>) {
//    protected val ioScope = CoroutineScope(Dispatchers.IO)
    protected lateinit var scope: CoroutineScope private set
    @Composable
    fun Main(params: P) {
        scope = rememberCoroutineScope()
        val state = model.CreateState(params)
        UI(state)
    }

    /**
     * Your ui
     */
    @Composable
    protected abstract fun UI(state: S)

    /**
     * Use sealed class
     */
    abstract class AbsUIAction

    /**
     * Use data class
     */
    abstract class AbsUIState<A: AbsUIAction> ()

    /**
     * In order to isolate the UI and data logic
     */
    abstract class AbsUIModel<P: Any?, S: AbsUIState<A>, A: AbsUIAction> () {
        @Composable
        internal abstract fun CreateState(params: P): S
        protected abstract fun actionExecute(params: P, action: A)
    }
}