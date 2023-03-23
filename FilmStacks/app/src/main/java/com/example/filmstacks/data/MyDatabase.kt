package com.example.filmstacks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        const val DATABASE_NAME = "my-db"
    }
}