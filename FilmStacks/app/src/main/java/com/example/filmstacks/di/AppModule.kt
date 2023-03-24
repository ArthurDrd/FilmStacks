package com.example.filmstacks.di

import android.content.Context
import androidx.room.Room
import com.example.filmstacks.data.local.MovieDao
import com.example.filmstacks.data.local.MovieDatabase
import com.example.filmstacks.data.remote.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideMovieDatabase(): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db").build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.movieDao()

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
