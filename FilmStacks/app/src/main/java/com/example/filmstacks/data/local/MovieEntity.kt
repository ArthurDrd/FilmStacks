package com.example.filmstacks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val adult: Boolean?,
    val backdrop_path: String?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double?,
    val budget: Int?,
    val popularity: Double?,
    val runtime: Int?,
    val status: String?,
    val genres: List<GenreEntity>? = null,
    val production_companies: List<CompaniesEntity>? = null
)

@Entity
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity
data class CompaniesEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val logo_path: String?,
    val origin_country: String
)