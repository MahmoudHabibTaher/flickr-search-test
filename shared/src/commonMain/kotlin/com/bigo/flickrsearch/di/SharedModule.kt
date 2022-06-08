package com.bigo.flickrsearch.di

import com.bigo.flickrsearch.core.di.coreModule
import com.bigo.flickrsearch.photos.di.photosModule
import org.koin.dsl.module

val sharedModule = module {
    includes(coreModule, photosModule)
}