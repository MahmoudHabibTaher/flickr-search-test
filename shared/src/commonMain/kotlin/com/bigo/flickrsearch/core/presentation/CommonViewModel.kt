package com.bigo.flickrsearch.core.presentation

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

expect open class BaseViewModel(coroutineContextProvider: CoroutineContextProvider) {
    protected val mainScope: CoroutineScope
}

abstract class CommonViewModel<S>(
    initialState: S,
    coroutineContextProvider: CoroutineContextProvider,
) : BaseViewModel(coroutineContextProvider) {
    protected val mainContext: CoroutineContext = coroutineContextProvider.main
    protected val backgroundContext: CoroutineContext = coroutineContextProvider.default
    private val mutableStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    val stateFlow: StateFlow<S> get() = mutableStateFlow.asStateFlow()

    val state: S get() = stateFlow.value

    protected suspend fun emit(state: S) {
        withContext(mainContext) {
            mutableStateFlow.emit(state)
        }
    }

    protected fun launchMain(block: suspend CoroutineScope.() -> Unit) =
        mainScope.launch(mainContext, block = block)

    protected fun launchBackground(block: suspend CoroutineScope.() -> Unit) =
        mainScope.launch(backgroundContext, block = block)
}