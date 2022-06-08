package com.bigo.flickrsearch.core.di

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import com.bigo.flickrsearch.core.coroutines.DefaultCoroutineContextProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::DefaultCoroutineContextProvider) bind CoroutineContextProvider::class
}