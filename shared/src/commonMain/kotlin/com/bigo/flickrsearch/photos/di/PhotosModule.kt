package com.bigo.flickrsearch.photos.di

import com.bigo.flickrsearch.photos.data.di.photosDataModule
import com.bigo.flickrsearch.photos.domain.di.photosDomainModule
import com.bigo.flickrsearch.photos.presentation.di.photosPresentationModule
import org.koin.dsl.module

val photosModule = module {
    includes(photosDataModule, photosDomainModule, photosPresentationModule)
}