package com.example.filmstacks.ui

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication {
                Surface(color = MaterialTheme.colors.background) {
                    MovieListScreen(
                        viewModel = viewModel(
                            factory = MovieListViewModelFactory(
                                repository = MovieRepository(
                                    api = TMDBApi.service,
                                    database = MyDatabase.getDatabase(context = this)
                                )
                            )
                        )
                    )
                }
            }
        }
    }
}
