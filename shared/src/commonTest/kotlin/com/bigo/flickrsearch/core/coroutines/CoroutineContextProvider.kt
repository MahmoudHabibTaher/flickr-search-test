package com.bigo.flickrsearch.core.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider(
    testDispatcher: CoroutineContext,
) : CoroutineContextProvider {
    override val main: CoroutineContext = testDispatcher
    override val default: CoroutineContext = testDispatcher

}