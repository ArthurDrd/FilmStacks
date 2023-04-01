package com.example.filmstacks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.filmstacks.data.MovieRepository
import com.example.filmstacks.data.local.MovieDatabase
import com.example.filmstacks.data.local.MovieEntity
import com.example.filmstacks.data.remote.ApiService
import com.example.filmstacks.di.AppModule
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = AppModule(this).provideApiService()
        val movieDatabase = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movie_database"
        ).build()

        val movieDao = movieDatabase.movieDao()
        movieRepository = MovieRepository(movieDao, apiService)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MovieList()
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MovieList() {
        var movies by remember { mutableStateOf(emptyList<MovieEntity>()) }
        var isLoading by remember { mutableStateOf(false) }

        val baseUrl = "https://image.tmdb.org/t/p/"
        val imageSize = "w500"

        // Prendre le poster path de l'image que tu veut afficher avec par ex
        // val posterPath = movie.poster_path
        // val posterUrl = "$baseUrl$imageSize$posterPath"

        LaunchedEffect(key1 = Unit) {
            movieRepository.updateMoviesFromApi()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Movie List", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.padding(8.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(movies) { movie ->
                        Text(movie.title)
                    }
                }
            }
        }

        lifecycleScope.launch {
            movieRepository.getAllMovies().collect { movieList ->
                movies = movieList
                isLoading = false
            }
        }
    }
}