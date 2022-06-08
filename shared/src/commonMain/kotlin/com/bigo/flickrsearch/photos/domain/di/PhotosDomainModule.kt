package com.bigo.flickrsearch.photos.domain.di

import com.bigo.flickrsearch.photos.data.PhotosRepository
import com.bigo.flickrsearch.photos.domain.SearchPhotos
import org.koin.dsl.module

val photosDomainModule = module {
    factory {
        SearchPhotos(get<PhotosRepository>())
    }
}