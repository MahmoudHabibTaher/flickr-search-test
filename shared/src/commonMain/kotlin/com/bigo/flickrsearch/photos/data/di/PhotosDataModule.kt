package com.bigo.flickrsearch.photos.data.di

import com.bigo.flickrsearch.photos.data.PhotosRepository
import com.bigo.flickrsearch.photos.data.remote.di.photosRemoteModule
import com.bigo.flickrsearch.photos.domain.PhotosDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val photosDataModule = module {
    includes(photosRemoteModule)

    singleOf(::PhotosRepository) bind PhotosDataSource::class
}