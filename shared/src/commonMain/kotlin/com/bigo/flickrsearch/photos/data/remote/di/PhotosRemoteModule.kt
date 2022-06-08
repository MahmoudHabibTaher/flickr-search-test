package com.bigo.flickrsearch.photos.data.remote.di

import com.bigo.flickrsearch.photos.data.remote.PhotosApi
import com.bigo.flickrsearch.photos.data.remote.PhotosKtorApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val photosRemoteModule = module {
    singleOf(::PhotosKtorApi) bind PhotosApi::class
}