package com.example.filmstacks.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.filmstacks.R
import com.example.filmstacks.data.local.MovieDao
import com.example.filmstacks.data.local.MovieEntity

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(movie: MovieEntity, navController: NavController, movieDao: MovieDao) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie.overview, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { movieDao.insert(movie.copy(isFavorite = !movie.isFavorite)) },
                modifier = Modifier.align(Alignment.End)
            ) {
                if (movie.isFavorite) {
                    Text(text = "Remove from favorites")
                } else {
                    Text(text = "Add to favorites")
                }
            }
        }
    }
}
