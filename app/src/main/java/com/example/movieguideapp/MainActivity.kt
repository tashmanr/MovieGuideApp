package com.example.movieguideapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MoviesRepository.discoverMovies( onSuccess = { movies ->
            Log.d("MainActivity", "Movies: $movies")
        },
            onError = {
                Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
            })
    }

}