package com.example.filmstacks

import com.example.filmstacks.data.local.MovieDatabase
import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MyApplication : Application() {
    val movieDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}