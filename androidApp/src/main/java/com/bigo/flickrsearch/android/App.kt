package com.bigo.flickrsearch.android

import android.app.Application
import com.bigo.flickrsearch.android.di.androidAppModules
import com.bigo.flickrsearch.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(sharedModule, androidAppModules)
        }
    }
}