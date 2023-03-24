package com.example.filmstacks.ui

import android.app.Application
import com.example.filmstacks.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(listOf(AppModule))
        }
    }
}
