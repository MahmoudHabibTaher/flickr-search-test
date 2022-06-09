package com.bigo.flickrsearch.photos.presentation.di

import com.bigo.flickrsearch.photos.presentation.PhotoDetailsViewModel
import com.bigo.flickrsearch.photos.presentation.PhotosSearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val photosPresentationModule = module {
    factoryOf(::PhotosSearchViewModel)
    factoryOf(::PhotoDetailsViewModel)
}