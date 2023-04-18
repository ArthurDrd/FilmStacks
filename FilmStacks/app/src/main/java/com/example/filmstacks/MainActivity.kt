package com.example.filmstacks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.filmstacks.data.MovieRepository
import com.example.filmstacks.data.local.MovieDao
import com.example.filmstacks.data.local.MovieDatabase
import com.example.filmstacks.data.local.MovieEntity
import com.example.filmstacks.data.remote.ApiService
import com.example.filmstacks.di.AppModule
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.filmstacks.data.local.GenreEntity
import kotlinx.coroutines.launch
import kotlin.random.Random

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
                    MovieList(apiService)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            downloadAndStoreMovieIds(movieDao, movieRepository)
            downloadAndStoreMovieDetails(movieRepository.movieDao, apiService)
        }
    }


    private fun downloadAndStoreMovieIds(movieDao: MovieDao, movieRepository: MovieRepository) {
        // Créez un client OkHttp pour télécharger le fichier TMDb
        val client = OkHttpClient()

        // Créez une demande pour télécharger le fichier TMDb
        val request = Request.Builder()
            .url("https://files.tmdb.org/p/exports/movie_ids_04_17_2023.json.gz")
            .build()

        // Exécutez la demande et obtenez la réponse
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Failed to download file: $response")

            // Décompressez le fichier téléchargé
            val gzipInputStream = GZIPInputStream(response.body?.byteStream())

            // Créez un tampon de lecture pour le fichier décompressé
            val bufferedReader = BufferedReader(InputStreamReader(gzipInputStream))

            // Lisez chaque ligne du fichier et stockez l'ID du film sur Room
            val movieIds = mutableListOf<Int>()
            var count = 0 // initialisez le compteur à zéro
            bufferedReader.useLines { lines ->
                lines.forEach { line ->
                    // Vérifiez si le compteur a atteint 500
                    if (count >= 500) {
                        return@forEach // Sortez de la boucle forEach
                    }

                    // Analysez la ligne en tant qu'objet JSON
                    val jsonObject = Gson().fromJson(line, JsonObject::class.java)

                    // Obtenez l'ID du film à partir de l'objet JSON
                    val movieId = jsonObject.get("id").asInt

                    // Ajoutez l'ID du film à la liste
                    movieIds.add(movieId)

                    // Incrémentez le compteur
                    count++
                }
            }

            // Stockez la liste des ID de film sur Room en utilisant une coroutine
            CoroutineScope(Dispatchers.IO).launch {
                movieIds.forEach { movieId ->
                    val movieEntity = MovieEntity(
                        id = movieId,
                        title = "",
                        overview = "",
                        adult = null,
                        backdrop_path = null,
                        poster_path = null,
                        release_date = null,
                        vote_average = null,
                        budget = 0,
                        popularity = 0.0,
                        runtime = 0,
                        status = "",
                        genres = emptyList()
                    )
                    movieDao.insert(movieEntity)

                }
            }
        }
    }

    private suspend fun downloadAndStoreMovieDetails(movieDao: MovieDao, apiService: ApiService) {
        // Récupérez tous les ID de film stockés sur Room
        val movieIds = movieDao.getAllIds()

        movieIds.forEach { movieId ->
            // Récupérez les détails du film à partir de l'API
            val movieDetails = apiService.getMovieById(movieId, "dc29076f48d72ca04e4eec9c68352fac")

            // Récupérez les genres du film à partir de l'API
            val genres = mutableListOf<GenreEntity>()
            movieDetails.genres?.forEach { genre ->
                val genreEntity = GenreEntity(
                    id = genre.id,
                    name = genre.name
                )
                genres.add(genreEntity)
            }

            // Stockez les détails du film et ses genres sur Room
            val movieEntity = MovieEntity(
                id = movieDetails.id,
                title = movieDetails.title,
                overview = movieDetails.overview,
                adult = movieDetails.adult,
                backdrop_path = movieDetails.backdrop_path,
                poster_path = movieDetails.poster_path,
                release_date = movieDetails.release_date,
                vote_average = movieDetails.vote_average,
                budget = movieDetails.budget,
                popularity = movieDetails.popularity,
                runtime = movieDetails.runtime,
                status = movieDetails.status,
                genres = genres
            )
            movieDao.update(movieEntity)
        }
    }



    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MovieList(apiService: ApiService) {
        var movies by remember { mutableStateOf(emptyList<MovieEntity>()) }
        var isLoading by remember { mutableStateOf(true) }

        val baseUrl = "https://image.tmdb.org/t/p/"
        val imageSize = "w500"

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Movie List", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.padding(8.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(movies) { movie ->
                        Column {
                            Text(movie.title, style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(movie.overview, style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.padding(4.dp))
                            if (!movie.genres.isNullOrEmpty()) {
                                Text(
                                    movie.genres.joinToString(separator = ", ") { it.name },
                                    style = MaterialTheme.typography.body2
                                )
                            }
                            Spacer(modifier = Modifier.padding(4.dp))
                            Image(
                                painter = rememberImagePainter(
                                    data = baseUrl + imageSize + movie.poster_path,
                                    builder = {
                                        crossfade(true)
                                    }
                                ),
                                contentDescription = movie.title,
                                modifier = Modifier.size(200.dp)
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }

        // Récupérez tous les films stockés sur Room en utilisant une coroutine
        lifecycleScope.launch {
            movieRepository.getAllMovies().collect { movieList ->
                movies = movieList
                isLoading = false
            }
        }

        // Téléchargez et stockez les détails de chaque film en utilisant une coroutine
        lifecycleScope.launch(Dispatchers.IO) {
            downloadAndStoreMovieDetails(movieRepository.movieDao, apiService)
        }
    }
}