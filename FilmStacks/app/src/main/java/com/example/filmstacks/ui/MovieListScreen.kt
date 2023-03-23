package com.example.filmstacks.ui

@Composable
fun MovieListScreen(viewModel: MovieListViewModel) {
    val movies by viewModel.movies.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        viewModel.loadMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        content = {
            MovieList(movies = movies)

