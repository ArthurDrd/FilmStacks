package com.example.filmstacks

import android.app.Application
import com.example.filmstacks.data.MyDatabase

class MyApplication : Application() {
    val database: MyDatabase by lazy { MyDatabase.getDatabase(this) }
}

