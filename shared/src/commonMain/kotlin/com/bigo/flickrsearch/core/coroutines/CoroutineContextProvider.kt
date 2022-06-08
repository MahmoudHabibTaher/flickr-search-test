package com.bigo.flickrsearch.core.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext
    val default: CoroutineContext
}

class DefaultCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineContext = Dispatchers.Main
    override val default: CoroutineContext = Dispatchers.Default
}