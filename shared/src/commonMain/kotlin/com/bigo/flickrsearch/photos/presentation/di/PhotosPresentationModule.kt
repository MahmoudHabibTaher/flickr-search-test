package com.bigo.flickrsearch.photos.presentation.di

import com.bigo.flickrsearch.photos.presentation.PhotosSearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val photosPresentationModule = module {
    singleOf(::PhotosSearchViewModel)
}