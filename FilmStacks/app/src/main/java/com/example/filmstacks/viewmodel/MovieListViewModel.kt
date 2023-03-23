package com.example.filmstacks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.filmstacks.data.local.db.MyDatabase

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MyDatabase.getDatabase(application)
    private val dao = database.myDao()

    val movies = dao.getAllMovies().asLiveData()
}