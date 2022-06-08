package com.bigo.flickrsearch.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope

actual open class BaseViewModel actual constructor(
    coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    protected actual val mainScope: CoroutineScope = viewModelScope
}