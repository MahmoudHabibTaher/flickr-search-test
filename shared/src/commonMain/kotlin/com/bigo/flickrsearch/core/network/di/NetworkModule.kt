package com.bigo.flickrsearch.core.network.di

import com.bigo.flickrsearch.core.network.ClientFactory
import com.bigo.flickrsearch.core.network.DefaultFlickrApiSettings
import com.bigo.flickrsearch.core.network.FlickrApiSettings
import org.koin.dsl.module
import org.koin.dsl.bind

val networkModule = module {
    single { ClientFactory.createKtorHttpClient() }
    single { DefaultFlickrApiSettings } bind FlickrApiSettings::class
}