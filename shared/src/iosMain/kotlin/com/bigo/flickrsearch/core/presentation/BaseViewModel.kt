package com.bigo.flickrsearch.core.presentation

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope

actual open class BaseViewModel actual constructor(
    coroutineContextProvider: CoroutineContextProvider
) {
    protected actual val mainScope: CoroutineScope = CoroutineScope(coroutineContextProvider.main)
}