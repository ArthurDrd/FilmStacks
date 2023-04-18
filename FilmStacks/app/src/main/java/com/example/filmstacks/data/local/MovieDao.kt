package com.example.filmstacks.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT id FROM movieentity")
    fun getAllIds(): List<Int>

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT name FROM GenreEntity WHERE id = :genreId")
    suspend fun getGenreNameById(genreId: Int): String?
}
