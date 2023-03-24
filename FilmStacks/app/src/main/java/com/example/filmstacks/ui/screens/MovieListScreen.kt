package com.example.filmstacks.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.filmstacks.R
import com.example.filmstacks.data.local.MovieDao
import com.example.filmstacks.data.local.MovieEntity
import com.example.filmstacks.ui.theme.MovieAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun MovieListScreen(navController: NavController, movieDao: MovieDao) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_movie")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Movie"
                )
            }
        }
    ) { innerPadding ->
        val movies = movieDao.getAllMovies()
        ScrollableColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            if (movies.isNotEmpty()) {
                for (movie in movies) {
                    MovieListItem(movie = movie, navController = navController)
                }
            } else {
                Text(
                    text = "No movies found",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Composable
fun MovieListItem(movie: MovieEntity, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate("movie_details/${movie.id}")
            })
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = movie.poster),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.h6)
            Text(text = movie.releaseDate, style = MaterialTheme.typography.subtitle1)
            Text(text = movie.overview, style = MaterialTheme.typography.body2)
        }
    }
}
