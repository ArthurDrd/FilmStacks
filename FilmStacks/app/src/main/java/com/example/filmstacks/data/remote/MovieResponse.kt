package com.example.filmstacks.data.remote

import com.example.filmstacks.data.local.MovieEntity

data class MovieResponse(
    val results: List<MovieEntity>
)
