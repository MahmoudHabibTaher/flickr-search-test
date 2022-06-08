package com.bigo.flickrsearch.photos.data.di

import com.bigo.flickrsearch.photos.data.PhotosRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val photosDataModule = module {
    singleOf(::PhotosRepository)
}