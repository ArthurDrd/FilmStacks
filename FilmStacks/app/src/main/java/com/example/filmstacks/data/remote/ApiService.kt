    package com.example.filmstacks.data.remote

    import com.example.filmstacks.data.local.MovieEntity
    import retrofit2.http.GET
    import retrofit2.http.Path
    import retrofit2.http.Query


    interface ApiService {
        @GET("movie/{movie_id}")
        suspend fun getMovieById(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String
        ): MovieEntity
    }