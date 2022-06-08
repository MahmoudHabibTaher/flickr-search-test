package com.bigo.flickrsearch.android.di

import com.bigo.flickrsearch.android.photos.di.photosAndroidModule
import org.koin.dsl.module

val androidAppModules = module {
    includes(photosAndroidModule)
}