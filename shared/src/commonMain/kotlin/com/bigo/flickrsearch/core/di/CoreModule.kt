package com.bigo.flickrsearch.core.di

import com.bigo.flickrsearch.core.coroutines.CoroutineContextProvider
import com.bigo.flickrsearch.core.coroutines.DefaultCoroutineContextProvider
import com.bigo.flickrsearch.core.network.ClientFactory
import com.bigo.flickrsearch.core.network.di.networkModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    includes(networkModule)

    singleOf(::DefaultCoroutineContextProvider) bind CoroutineContextProvider::class
}