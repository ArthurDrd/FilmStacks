package com.example.filmstacks.data

import com.example.filmstacks.data.local.MovieDao
import com.example.filmstacks.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import com.example.filmstacks.data.local.MovieEntity


class MovieRepository(val movieDao: MovieDao, private val apiService: ApiService) {

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getMovieById(movieId: Int): Flow<MovieEntity?> = movieDao.getMovieById(movieId)

    companion object {
        private const val API_KEY = "dc29076f48d72ca04e4eec9c68352fac"
    }
}