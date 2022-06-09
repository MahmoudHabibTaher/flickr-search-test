package com.bigo.flickrsearch.android.photos.di

import com.bigo.flickrsearch.photos.presentation.PhotoDetailsViewModel
import com.bigo.flickrsearch.photos.presentation.PhotosSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photosAndroidModule = module {
    viewModel { PhotosSearchViewModel(get(), get()) }

    viewModel { PhotoDetailsViewModel(get(), get()) }
}