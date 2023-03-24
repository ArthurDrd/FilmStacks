package com.example.filmstacks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.filmstacks.ui.theme.MoviesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesApp()
}

@Composable
fun MoviesApp() {
    MoviesTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                // TODO: add your Composables here
            }
        }
    }
}
